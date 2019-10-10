package exercise;

public class RPNCalculator {


	public static void main(String[] args) {
		SimpleWindow gw = new SimpleWindow("Calculator");
		DoubleStack stack = new DoubleStack();
		while (true){
			if (stack.depth()==0) {
				gw.clear();
				gw.addString("[empty]\n");	
				gw.addString("Commands: q=quit c=clear + - * / number");
			} else {
				gw.clear();
				gw.addString(stack.toString());	
			}
			String input = gw.getString().trim();
			if (input.equals("")) {
				input = " ";
			}
			char command = input.charAt(0);
			if (Character.isDigit(command)){
				double value = Double.parseDouble(input);
				stack.push(value);
			} else if(command == '+') {
				double v1 = stack.pop();
				double v2 = stack.pop();
				stack.push(v1+v2);
			} else if(command == '-') {
				double v1 = stack.pop();
				double v2 = stack.pop();
				stack.push(v2-v1);
			} else if(command == '*') {
				double v1 = stack.pop();
				double v2 = stack.pop();
				stack.push(v1*v2);
			} else if(command == '/') {
				double v1 = stack.pop();
				double v2 = stack.pop();
				stack.push(v2/v1);	
			} else if(command == 'c') {
				stack.clear();
				gw.clear();
			} else if(command == 'q') {
				stack.clear();
				gw.exit();
			}else {
				gw.addString("Illegal command\n");
			}		
			
		}

	}

}
