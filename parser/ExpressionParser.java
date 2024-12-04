package expression.parser;

import expression.*;
import java.util.Objects;


public class ExpressionParser implements MyParser {
    public MyExpression parse(String exp) {
        exp = withoutSpaces(exp);
        int am = amount(exp);
        return parse(exp, am);
    }
    private MyExpression parse(String exp, int ops) {
        while (exp.charAt(0) == '(' && exp.charAt(exp.length() - 1) == ')' && ops == 0) {
            exp = exp.substring(1, exp.length() - 1);
            ops = amount(exp);
        }
        int newops = 0;
        char mainOp = ' ';
        int pos = 0;
        int numOfOp = 0;
        int bracketsWas = 0;
        if (ops == 0) {
            boolean unarMinus = exp.length() > 1
                    && (exp.charAt(0) == '-' && !Character.isDigit(exp.charAt(1)));
            boolean unarNot = exp.length() > 1 && (exp.charAt(0) == '~');
            try {
                int a;
                if (unarMinus || unarNot) {
                    if (exp.length() > 2 && exp.charAt(1) == '(' && exp.charAt(exp.length() - 1) == ')') {
                        a = Integer.parseInt(exp.substring(2, exp.length() - 1));
                    }
                    else {
                        a = Integer.parseInt(exp.substring(1));
                    }
                } else {
                    if (exp.length() > 1 && getPriority(exp.charAt(0)) == 1000) {
                        a = Integer.parseInt(exp.substring(1));
                    } else {
                        a = Integer.parseInt(exp);
                    }
                }
                if (unarMinus) {
                    return new Minus(new Const(a));
                } else if (unarNot) {
                    return new Not(new Const(a));
                } else {
                    return new Const(a);
                }
            } catch (NumberFormatException e) {
                String var;
                if ((unarMinus || unarNot) && exp.charAt(1) == '(' && exp.charAt(exp.length() - 1) == ')') {
                    var = exp.substring(2, exp.length() - 1);
                } else {
                    var = unarMinus || unarNot ? exp.substring(1) : exp;
                }
                if (unarMinus) {
                    return new Minus(new Variable(var));
                } else if (unarNot) {
                    return new Not(new Variable(var));
                } else {
                    return new Variable(var);
                }
            }
        }
        char potUnar = ' ';
        for (int i = 0; i < exp.length(); i++) {
            boolean is = isOp(exp, i);
            if (is && bracketsWas == 0) {
                newops++;
            }
            if (exp.charAt(i) == '(') {
                bracketsWas++;
            } else if (exp.charAt(i) == ')') {
                bracketsWas--;
                continue;
            }
            if (!is) {
                continue;
            }
            if (bracketsWas != 0) {
                continue;
            }
            if (i == 0 && (exp.charAt(i) == '-' || exp.charAt(i) == '~')) {
                potUnar = exp.charAt(i);
                continue;
            }
            if ((exp.charAt(i) == '-' || exp.charAt(i) == '~') && i > 0 && getPriority(exp.charAt(i - 1)) < 100) {
                potUnar = getPriority(exp.charAt(i)) > potUnar ? exp.charAt(i) : potUnar;
                continue;
            }
            if (getPriority(exp.charAt(i)) <= getPriority(mainOp)) {
                mainOp = exp.charAt(i);
                pos = i;
                numOfOp = newops;
            }
        }
        MyExpression ex1 = parse(pos != 0 ? exp.substring(0, pos) : exp.substring(1), numOfOp == 0 ? newops - 1 : numOfOp - 1);
        MyExpression ex2 = pos != 0 ? parse(exp.substring(pos + 1), newops - numOfOp) : null;
        if (ex2 == null) {
            mainOp = potUnar;
        }
        return makeExp(mainOp, ex1, ex2);
    }
    private String withoutSpaces(String exp) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            if (getPriority(exp.charAt(i)) != 1000) {
                sb.append(exp.charAt(i));
            } else {
                if (!sb.isEmpty() && getPriority(sb.charAt(sb.length() - 1)) >= 100 && i > 0 && exp.charAt(i - 1) == '-' && Character.isDigit(exp.charAt(i + 1))) {
                    sb.append(exp.charAt(i));
                }
            }
        }
        String s = sb.toString();
        int a = amount(s);
        while (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')' && a == 0) {
            s = s.substring(1, s.length() - 1);
            a = amount(s);
        }
        return s;
    }
    private int amount(String exp) {
        int bracketsWas = 0;
        int am = 0;
        for (int i = 0; i < exp.length() - 1; i++) {
            if (exp.charAt(i) == '(') {
                bracketsWas++;
            } else if (exp.charAt(i) == ')') {
                bracketsWas--;
            }
            if (bracketsWas != 0) {
                continue;
            }
            if (isOp(exp, i)) {
                am++;
            }
        }
        return am;
    }
    private boolean isOp(String check, int pos) {
        char ch0 = pos > 0 ? check.charAt(pos - 1) : 'p';
        char ch1 = check.charAt(pos);
        char ch2 = pos < check.length() - 1 ? check.charAt(pos + 1) : 'p';
        char ch3 = pos < check.length() - 2 ? check.charAt(pos + 2) : 'p';
        if (getPriority(ch1) < 100) {
            if (ch1 == '-' || ch1 == '~') {
                if (ch2 == '(') {
                    return true;
                }
                else if (Character.isDigit(ch2)) {
                    return ch0 == ')' || (ch0 != 'p' && Character.isLetter(ch0)) || Character.isDigit(ch0);
                } else if (Character.isLetter(ch2)) {
                    return true;
                }
                else return ch2 == '-' || ch2 == '~';
            }
            if (getPriority(ch0) < 100 && getPriority(ch2) <= 100) {
                return true;
            }
            if (ch3 != 'p' && ch2 == ' ' && Character.isDigit(ch3)) {
                return true;
            }
            return ch0 == 'p' || getPriority(ch0) >= 100;
        }
        return false;
    }
    private int getPriority(char c) {
        if (Character.isDigit(c)) {
            return 100;
        }
        return switch (c) {
            case '~' -> 6;
            case '*', '/' -> 5;
            case '-', '+' -> 4;
            case '&' -> 3;
            case '^' -> 2;
            case '|' -> 1;
            case 'x', 'y', 'z', '(', ')' -> 100;
            case 'p' -> -1000000000;
            default -> 1000;
        };
    }
    private MyExpression makeExp(char type, MyExpression ex1, MyExpression ex2) {
        if (Objects.isNull(ex2)) {
            if (type == '-') {
                return new Minus(ex1);
            } else {
                return new Not(ex1);
            }
        }
        return switch (type) {
            case '+' -> new Add(ex1, ex2);
            case '-' -> new Subtract(ex1, ex2);
            case '*' -> new Multiply(ex1, ex2);
            case '/' -> new Divide(ex1, ex2);
            case '&' -> new And(ex1, ex2);
            case '^' -> new Xor(ex1, ex2);
            case '|' -> new Or(ex1, ex2);
            case '~' -> new Not(ex1);
            default -> null;
        };
    }
    public static void main(String[] args) {
        MyExpression exp = new ExpressionParser().parse("(17322361 | -1527258051) - (x ^ 572142912)");
        System.out.println(exp);
    }
}
