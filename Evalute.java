static String evalute(String input)
  {
		return  compute_postfix(infixToPostfix(process(input)));
	}
	static boolean isNumber(char c)
	{
		return c>='0' && c<='9';
			
	}
	static boolean isOperator(char c)
	{
		return c=='+' || c=='-' || c=='*'|| c=='/' || c=='^';
	}
	static boolean isOperator(String op)
	{
		return op.equals("+") || op.equals("-") || op.equals("*") ||op.equals("/") || op.equals("^");
	}
	static int order(String op)
	{
		if(op.equals("+") || op.equals("-"))
			return 0;
		if(op.equals("*") || op.equals("/"))
			return 1;
		if(op.equals("^"))
			return 2;
		else
			return -1;
	}
	static  ArrayList<String> process(String input)
	{
		ArrayList<String> list=new ArrayList<String>();
		
		StringBuilder integer=new StringBuilder();
		
		for(int i=0;i<input.length();i++)
		{
			char c=input.charAt(i);
			if(isNumber(c))
			{
				integer.append(c);
			}
			else if(c=='-' && i==0)
			{
				list.add("0");
				list.add("-");
				
			}
			
			else if(c=='-' && (input.charAt(i-1)=='(' || isOperator(input.charAt(i-1))  ))
			{

				integer.append(c);
			}
			else if(c=='.')
			{
				integer.append(c);
			}
			else if(c==' ')
			{
				continue;
			}
			else
			{	if(integer.length()>0)
					list.add(integer.toString());
				list.add(c+"");
				integer=new StringBuilder();
			}
		}
		if(integer.length()>0)
			list.add(integer.toString());
		
		
		
		return list;
		
	}

	static ArrayList<String> infixToPostfix(ArrayList<String> input)
	{
		
		ArrayList<String> postfix=new ArrayList<String>();
		Stack<String> st=new Stack<String>();
		for(int i=0;i<input.size();i++)
		{
			String temp=input.get(i);
			if(isOperator(temp))
			{
				//operator
				while(st.isEmpty()==false)
				{
					String item=st.peek();
					if(item.equals("("))
						break;
					if(order(item)>order(temp))
					{
						postfix.add(item);
						st.pop();
					}
					else if(order(item)==order(temp) && item.equals("^")==false)
					{
						postfix.add(item);
						st.pop();
					}
					else
					{
						break;
					}
				}
				st.push(temp);
			}
			else if(temp.equals("("))
			{
				st.push(temp);
				
			}
			else if(temp.equals(")"))
			{
				while(st.isEmpty()==false)
				{
					String item=st.pop();
					if(item.equals("("))
						break;
					postfix.add(item);
				}
				
			}
			else
			{
				//integer
				postfix.add(temp);
			}
				
		}
		while(st.isEmpty()==false)
		{
			postfix.add(st.pop());
		}
		
		return postfix;
	}
	
	static String compute_postfix(ArrayList<String> postfix)
	{

		Stack<Double> st=new Stack<Double>();
	
		for(int i=0;i<postfix.size();i++)
		{
			String temp=postfix.get(i);
			
			if(temp.equals("+"))
			{
				double y=st.pop();
				double x=st.pop();
				st.push(x+y);
			}
			else if(temp.equals("-"))
			{
				double y=st.pop();
				double x=st.pop();
				st.push(x-y);
			}
			else if(temp.equals("*"))
			{
				double y=st.pop();
				double x=st.pop();
				st.push(x*y);
			}
			else if(temp.equals("/"))
			{
				double y=st.pop();
				double x=st.pop();
				st.push(x/y);
			}
			else if(temp.equals("^"))
			{
				double y=st.pop();
				double x=st.pop();
				st.push(Math.pow(x, y));
			}
			else
			{
			
				st.push(Double.parseDouble(temp));
			}
		}
		DecimalFormat myFormatter = new DecimalFormat("###.#####");
		return myFormatter.format(st.pop());
		
	}
