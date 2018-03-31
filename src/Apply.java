import java.util.ArrayList;
import java.util.HashMap;


public class Apply {
	ArrayList <Node> T,T1,T2;
	HashMap <Node,Integer> H,H1,H2;
	int vars,nodeCount;
	String op = "&";
	int x[];
	int anysat[];
	int j;int b;int res,satCount;

	public Apply(int n)
	{
		nodeCount = 0;
		vars = n; 
		x = new int[n];
		anysat = new int[n];
		for(int i=0;i<n;i++)
		{
			x[i] = -1;
			anysat[i] = -1;
		}
		this.T = new ArrayList();
		this.H = new HashMap();
		this.T1 = new ArrayList();
		this.H1 = new HashMap();
		this.T2 = new ArrayList();
		this.H2 = new HashMap();
        addNode(new Node(false, vars), 0);
        addNode(new Node(true, vars), 1);
        addNode1(new Node(false, vars), 0);
        addNode1(new Node(true, vars), 1);
        addNode2(new Node(false, vars), 0);
        addNode2(new Node(true, vars), 1);
	}
    public boolean contains(Node n)
    {
        return H.containsKey(n);
    }
    public void removeNode(int index)
    {
        H.remove(T.get(index));
        T.remove(index);
    }
     int addNode(Node n)
    {
        T.add(n);
        int index = T.size() - 1;
        H.put(n, index);
        return index;
    }
     void addNode(Node n, int index)
    {
        T.add(index, n);
        H.put(n, index);
    }
    
     public int getNodeIndex(Node n)
     {
         return H.get(n);
     }
     public Node getNode(int index)
     {
         return T.get(index);
     }
     public boolean contains1(Node n)
     {
         return H1.containsKey(n);
     }
     public void removeNode1(int index)
     {
         H1.remove(T1.get(index));
         T1.remove(index);
     }
      int addNode1(Node n)
     {
         T1.add(n);
         int index = T1.size() - 1;
         H1.put(n, index);
         return index;
     }
      void addNode1(Node n, int index)
     {
         T1.add(index, n);
         H1.put(n, index);
     }
     
      public int getNodeIndex1(Node n)
      {
          return H1.get(n);
      }
      public Node getNode1(int index)
      {
          return T1.get(index);
      }
      public boolean contains2(Node n)
      {
          return H2.containsKey(n);
      }
      public void removeNode2(int index)
      {
          H2.remove(T2.get(index));
          T2.remove(index);
      }
       int addNode2(Node n)
      {
          T2.add(n);
          int index = T2.size() - 1;
          H2.put(n, index);
          return index;
      }
       void addNode2(Node n, int index)
      {
          T2.add(index, n);
          H2.put(n, index);
      }
      
       public int getNodeIndex2(Node n)
       {
           return H2.get(n);
       }
       public Node getNode2(int index)
       {
           return T2.get(index);
       }
     
	int pair(int i,int j)
    {
        return (((i+j)*(i+j+1))/2 + i);
    } 
	boolean eval1()
	{
		boolean exp = ((and(imp(not(x[0]),equiv(1,x[1])),not(x[1]))) == 1)?true:false;
		//boolean exp = ((equiv(and(x[0],x[1]),x[2])) == 1)?true:false;
		return exp;
	}
	boolean eval2()
	{
		boolean exp = ((or(equiv(x[0],x[1]),x[2])) == 1)?true:false;
		return exp;
	}
	int and(int a, int b)
	{
		int ans = 0;
		if((a == 0) || (b == 0))
			ans = 0;
		else
			ans = 1;
		return ans;
	}
	int or(int a, int b)
	{
		int ans = 0;
		if((a == 0) && (b == 0))
			ans = 0;
		else
			ans = 1;
		return ans;
	}
	int not(int a)
	{
		int ans = 0;
		if(a == 0)
			ans = 1;
		else
			ans = 0;
		return ans;
	}
	int imp(int a, int b)
	{
		int ans = 0;
		if(a == 1 && b == 0)
			ans = 0;
		else
			ans = 1;
		return ans;
	}
	int equiv(int a, int b)
	{
		int ans = 0;
		if(a == b)
			ans = 1;
		else
			ans = 0;
		return ans;
	}

    public int build(int i)
    {
        if(i+1 > vars)
        {
            if(eval1()) 
            	return 1;
            else 
            	return 0;
        }
        else
        {
        	x[i] = 0;
            int l = build1(i + 1);
            x[i] = 1;
            int h = build1(i + 1);
            Node n = new Node(l,h,i);
            return mk1(n);
        }
    }
	
    public int build1(int i)
    {
        if(i+1 > vars)
        {
            if(eval1()) 
            	return 1;
            else 
            	return 0;
        }
        else
        {
        	x[i] = 0;
            int l = build1(i + 1);
            x[i] = 1;
            int h = build1(i + 1);
            Node n = new Node(l,h,i);
            return mk1(n);
        }
    }
	
    public int build2(int i)
    {
        if(i+1 > vars)
        {
            if(eval2()) 
            	return 1;
            else 
            	return 0;
        }
        else
        {
        	x[i] = 0;
            int l = build2(i + 1);
            x[i] = 1;
            int h = build2(i + 1);
            Node n = new Node(l,h,i);
            return mk2(n);
        }
    }
    
    int mk(Node n)
    {
        if(n.low == n.high)
        {
            return n.low;            
        }
        else if(contains(n))
        {
            return getNodeIndex(n);
        }
        else
        { 
           return addNode(n);
        }        
    }
    
    int mk1(Node n)
    {
        if(n.low == n.high)
        {
            return n.low;            
        }
        else if(contains1(n))
        {
            return getNodeIndex1(n);
        }
        else
        { 
           return addNode1(n);
        }        
    }

    int mk2(Node n)
    {
        if(n.low == n.high)
        {
            return n.low;            
        }
        else if(contains2(n))
        {
            return getNodeIndex2(n);
        }
        else
        { 
           return addNode2(n);
        }        
    }
    
    boolean op(String o,boolean a, boolean b)
    {
    	boolean result = a & b;
    	return result;
    }

	void apply(Apply a1,Apply a2)
    {
		T1 = a1.T1;
		T2 = a2.T2;
		T1.get(0).index = 5;
		T1.get(1).index = 5;
		T2.get(0).index = 5;
		T2.get(1).index = 5;
    	apply_(T1.get(T1.size()-1),T2.get(T2.size()-1));
		System.out.println("Table T after apply:");
		System.out.println("Format : var(u),high(u),low(u)");
		for(int i=0;i<T.size();i++)
		{
			System.out.print("u ="+i+": " + (((T.get(i)).index)) + "," + ((T.get(i)).high) + "," + ((T.get(i)).low));
			System.out.println();
		}
    }
    
    private int apply_(Node u1, Node u2)
    {
    	if((u1.low == -1) && (u1.high == -1) && (u2.low == -1) && (u2.high == -1))
    	{
        	Node n = new Node(u1);
    		n.terminalVal = op(op,u1.terminalVal,u2.terminalVal);
    		return (n.terminalVal?1:0);
    	}
    	else if(u1.index == u2.index)
    	{
    		int l = apply_(T1.get(u1.low),T2.get(u2.low));
    		int h = apply_(T1.get(u1.high),T2.get(u2.high));
    		Node n = new Node(l,h,u1.index);
    		return mk(n);
    		//return u1.index;
    	}
    	else if(u1.index < u2.index)
    	{
    		int l = apply_(T1.get(u1.low),u2);
    		int h = apply_(T2.get(u1.high),u2);
    		Node n = new Node(l,h,u1.index);
    		return mk(n);
    		//return u1.index;
    	}
    	else 
    	{
    		int l = apply_(u1,T2.get(u2.low));
    		int h = apply_(u1,T2.get(u2.high));
    		Node n = new Node(l,h,u2.index);
    		return mk(n);
    		//return u2.index;
    	}
    }
   	public static void main(String[] args)
   	{
   		System.out.println("Input Expression 1: ((and(imp(not(x[0]),equiv(1,x[1])),not(x[1])))");
   		System.out.println("Input Expression 2: (or(equiv(x[0],x[1]),x[2])");
   		System.out.println("Apply operation: &");
   		Apply a1 = new Apply(2);
   		Apply a2 = new Apply(3);
   		a1.build1(0);
   		a2.build2(0);
		System.out.println("Table T1 after build:");
		System.out.println("Format : var(u),high(u),low(u)");
		for(int i=0;i<a1.T1.size();i++)
		{
			System.out.print("u ="+i+": " + (((a1.T1.get(i)).index)) + "," + ((a1.T1.get(i)).high) + "," + ((a1.T1.get(i)).low));
			System.out.println();
		}
		System.out.println("Table T2 after build:");
		System.out.println("Format : var(u),high(u),low(u)");
		for(int i=0;i<a2.T2.size();i++)
		{
			System.out.print("u ="+i+": " + (((a2.T2.get(i)).index)) + "," + ((a2.T2.get(i)).high) + "," + ((a2.T2.get(i)).low));
			System.out.println();
		}
		long startTime = System.nanoTime();
		a1.apply(a1,a2);
		long stopTime = System.nanoTime();
		System.out.println("Execution time for apply "+ (stopTime - startTime) + " ns");
		System.out.println("\n");
   	}
}
