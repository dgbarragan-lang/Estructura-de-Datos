package Controller;

import java.util.Map;

import Model.StackList;

public class NotationConverter {
    private StackList stack; // internal stack

    public NotationConverter() {
        stack = new StackList();
    }
    // ---------------------------
    // Convert infix to postfix with steps
    // ---------------------------
    public String convertToPostfix(String infixExpression) throws Exception {
        stack.clear();
        StringBuilder result = new StringBuilder();
        StringBuilder steps = new StringBuilder();

        steps.append("Original infix: ").append(infixExpression).append("\n");
        steps.append("Conversion steps:\n");

        for (int i = 0; i < infixExpression.length(); i++) {
            char c = infixExpression.charAt(i);

            if (Character.isWhitespace(c)) continue;

            if (isOperator(c)) {
                while (!stack.isEmpty() && stackPriority((char) stack.peek()) >= infixPriority(c)) {
                    result.append(stack.pop()).append(" ");
                    steps.append("Pop operator from stack → ").append(result).append("\n");
                }
                stack.push(c);
                steps.append("Push operator '").append(c).append("' to stack\n");
            } 
            else if (c == '(') {
                stack.push(c);
                steps.append("Push '(' to stack\n");
            } 
            else if (c == ')') {
                while (!stack.isEmpty() && (char) stack.peek() != '(') {
                    result.append(stack.pop()).append(" ");
                    steps.append("Pop operator from stack → ").append(result).append("\n");
                }
                stack.pop(); // remove '('
                steps.append("Pop '(' from stack\n");
            } 
            else { // operand
                result.append(c).append(" "); //
                steps.append("Add operand '").append(c).append("' → ").append(result).append("\n");
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(" ");
            steps.append("Pop remaining operator → ").append(result).append("\n");
        }

        steps.append("\nFinal postfix: ").append(result.toString().trim());
        return steps.toString();
    }

    // ---------------------------
    // Convert infix to prefix
    // ---------------------------
    public String infixToPrefix(String infixExpression) throws Exception {
        // Step 1: reverse the infix expression
        String reversed = new StringBuilder(infixExpression).reverse().toString();
        StringBuilder swapped = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (c == '(') swapped.append(')');
            else if (c == ')') swapped.append('(');
            else swapped.append(c);
        }

        // Step 2: convert reversed expression to postfix
        String postfixSteps = convertToPostfix(swapped.toString());

        // Step 3: extract postfix expression (last line)
        String[] lines = postfixSteps.split("\n");
        String postfix = lines[lines.length - 1].replace("Final postfix: ", "").trim();

        // Step 4: reverse postfix to get prefix
        String prefix = new StringBuilder(postfix).reverse().toString();
        return prefix;
    }

    // ---------------------------
    // Evaluate postfix step by step
    // ---------------------------
    public String evaluatePostfixStepByStep(String postfixExpression, Map<Character, Double> variables) throws Exception {
        stack.clear();
        StringBuilder steps = new StringBuilder();
        steps.append("Step-by-step evaluation:\n");

        String[] tokens = postfixExpression.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (Character.isLetter(token.charAt(0))) {
                // Variable
                char var = token.charAt(0);
                double value = variables.getOrDefault(var, 0.0);
                stack.push(value);
                steps.append("Push variable ").append(var).append(" = ").append(value)
                     .append(" → Stack: ").append(stackToString()).append("\n");
            } else if (token.matches("\\d+(\\.\\d+)?")) {
                // Number
                double value = Double.parseDouble(token);
                stack.push(value);
                steps.append("Push number ").append(token)
                     .append(" → Stack: ").append(stackToString()).append("\n");
            } else if (isOperator(token.charAt(0))) {
                // Operator
                double b = (double) stack.pop();
                double a = (double) stack.pop();
                double res = operate(a, token.charAt(0), b);
                stack.push(res);
                steps.append("Operate: ").append(a).append(" ").append(token).append(" ").append(b)
                     .append(" = ").append(res).append(" → Stack: ").append(stackToString()).append("\n");
            } else {
                throw new Exception("Invalid token: " + token);
            }
        }

        double finalResult = (double) stack.pop();
        steps.append("Final result: ").append(finalResult).append("\n");
        return steps.toString();
    }
    
    public String infixToPrefixWithSteps(String infixExpression) throws Exception {
        StringBuilder steps = new StringBuilder();
        steps.append("Original infix: ").append(infixExpression).append("\n");
        
        // Step 1: reverse the infix expression
        String reversed = new StringBuilder(infixExpression).reverse().toString();
        StringBuilder swapped = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (c == '(') swapped.append(')');
            else if (c == ')') swapped.append('(');
            else swapped.append(c);
        }
        steps.append("Reversed and swapped parentheses: ").append(swapped).append("\n");

        // Step 2: convert reversed expression to postfix
        String postfixSteps = convertToPostfix(swapped.toString());
        steps.append("\nPostfix conversion steps (of reversed expression):\n");
        steps.append(postfixSteps).append("\n");

        // Step 3: extract postfix expression (last line)
        String[] lines = postfixSteps.split("\n");
        String postfix = lines[lines.length - 1].replace("Final postfix: ", "").trim();

        // Step 4: reverse postfix to get prefix
        String prefix = new StringBuilder(postfix).reverse().toString();
        steps.append("Final prefix expression: ").append(prefix).append("\n");

        return steps.toString();
    }

    // ---------------------------
    // Priorities
    // ---------------------------
    public int infixPriority(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
            default: return 0;
        }
    }

    public int stackPriority(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
            default: return 0;
        }
    }

    // ---------------------------
    // Operator check
    // ---------------------------
    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // ---------------------------
    // Operate two numbers
    // ---------------------------
    public double operate(double a, char operator, double b) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            case '^': return Math.pow(a, b);
            default: return 0;
        }
    }

    // ---------------------------
    // Convert stack to string (for GUI)
    // ---------------------------
    public String stackToString() {
        StringBuilder sb = new StringBuilder();
        StackList tempStack = new StackList();
        try {
            while (!stack.isEmpty()) {
                Object val = stack.pop();
                sb.insert(0, val + " ");
                tempStack.push(val);
            }
            // Restore original stack
            while (!tempStack.isEmpty()) {
                stack.push(tempStack.pop());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}