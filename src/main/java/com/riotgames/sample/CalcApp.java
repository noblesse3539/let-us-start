package com.riotgames.sample;

import java.util.Arrays;
import java.util.Stack;


/**
 * Calculator application
 */
public class CalcApp {
	private Stack<String> oStack;
	private String[] postfix;
	
    public double calc(String[] tokens) {
        final double firstOperand;
        final double secondOperand;
        firstOperand = Double.parseDouble(tokens[0]);
        if (tokens.length > 2) {
            secondOperand = Double.parseDouble(tokens[2]);
        } else {
            secondOperand = Double.parseDouble(tokens[1]);
        }
        final Operator operator = Operator.findOperator(tokens[1]);

        return operator.evaluate(firstOperand, secondOperand);

    }
    public boolean isNumeric(String s) {		// String 파라미터가 숫자인지 판별
    	  try {
    	      Double.parseDouble(s);
    	      return true;
    	  } catch(NumberFormatException e) {
    	      return false;
    	  }
    }
    public boolean token2Postfix(String[] tokens) {
		int i = 0;
		int p = 0;
		String curToken, poppedToken, topToken;

		this.oStack = new Stack<String>();
		this.postfix = new String[tokens.length];

		while (i < tokens.length) {
			curToken = tokens[i++];
			if (this.isNumeric(curToken) == true) {
				this.postfix[p++] = curToken;
			}else {
				if (curToken.charAt(0) == ')') {
					if (!this.oStack.isEmpty())
						poppedToken = this.oStack.pop();
					else
						return false;

					while (poppedToken.charAt(0) != '(') {
						this.postfix[p++] = poppedToken;
						if (!this.oStack.isEmpty())
							poppedToken = this.oStack.pop();
						else
							return false;
					}
				} else {
					int inComingP = inComingPrecedence(curToken);
					if (!this.oStack.isEmpty()) {
						topToken = this.oStack.peek();
						while (inStackPrecedence(topToken) >= inComingP) {
							poppedToken = this.oStack.pop();
							this.postfix[p++] = poppedToken;
							if (!this.oStack.isEmpty())
								topToken = this.oStack.peek();
							else
								break;
						}
					}
					this.oStack.push(curToken);
				}
			}
		}
		while(!this.oStack.isEmpty()){
			poppedToken = this.oStack.pop();
			this.postfix[p++] = poppedToken;
		}
		return true;
	}
    private int inComingPrecedence(String givenToken) {
		if (givenToken.charAt(0) == '+' || givenToken.charAt(0) == '-')
			return 12;
		else if (givenToken.charAt(0) == '*' || givenToken.charAt(0) == '/')
			return 13;
		else if (givenToken.charAt(0) == '(')
			return 20;
		else if (givenToken.charAt(0) == ')')
			return 19;
		else
			return 0;
	}
    private int inStackPrecedence(String givenToken) {
		if (givenToken.charAt(0) == '+' || givenToken.charAt(0) == '-')
			return 12;
		else if (givenToken.charAt(0) == '*' || givenToken.charAt(0) == '/')
			return 13;
		else if (givenToken.charAt(0) == '(')
			return 0;
		else if (givenToken.charAt(0) == ')')
			return 19;
		else
			return 0;
	}

    public static void main( String[] args ) {
        final CalcApp app = new CalcApp();
        final StringBuilder outputs = new StringBuilder();
        Arrays.asList(args).forEach(value -> outputs.append(value + " "));
        System.out.print( "Addition of values: " + outputs + " = ");
        System.out.println(app.calc(args));
        System.out.println("");

    }
}
