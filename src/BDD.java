import java.util.HashMap;
import java.util.ArrayList;

public class BDD 
{
	ArrayList <Node> T;
	HashMap <Node,Integer> H;
	int vars,nodeCount;
	int x[];
	int anysat[];
	int j;int b;int res,satCount;

	public BDD(int n)
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
        addNode(new Node(false, vars), 0);
        addNode(new Node(true, vars), 1);
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
     
	int pair(int i,int j)
    {
        return (((i+j)*(i+j+1))/2 + i);
    } 
	boolean eval()
	{
		//boolean exp = ((and(imp(not(x[0]),equiv(1,x[1])),not(x[1]))) == 1)?true:false;
		boolean exp = ((or(equiv(x[0],x[1]),x[2])) == 1)?true:false;
		//boolean exp = (or(and(equiv(x[0],x[1]),equiv(x[2],x[3])),equiv(x[4],x[5])) == 1)?true:false;
		return exp;
	}
 
    public int build(int i)
    {
        if(i+1 > vars)
        {
            if(eval()) 
            	return 1;
            else 
            	return 0;
        }
        else
        {
        	x[i] = 0;
            int l = build(i + 1);
            x[i] = 1;
            int h = build(i + 1);
            Node n = new Node(l,h,i);
            return mk(n);
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

    boolean op(String o,boolean a, boolean b)
    {
    	boolean result = false;
    	return result;
    }
 
    void restrict(BDD r,int var,int val)
    {
    	j = var;
    	b = val;
    	for(int i = 0; i<=r.T.size()-1; i++)
    	{
    		if(i != j)
    			rest(r,i);	
    	}

		System.out.println("Table T after restrict:");
		System.out.println("Format : var(u),high(u),low(u)");
		for(int i=0;i<T.size();i++)
		{
			System.out.print("Entry u ="+i+": " + (((T.get(i)).index)) + "," + ((T.get(i)).high) + "," + ((T.get(i)).low));
			System.out.println();
		}
    }
    private int rest(BDD r,int u)
    {
    	if(r.T.get(u).index > j)
    	{
    		mk(new Node(r.T.get(u).low,r.T.get(u).high,r.T.get(u).index));
    		return u;
    	}
    	else if(r.T.get(u).index < j)
    	{
    		return mk(new Node(rest(r,r.T.get(u).low),rest(r,r.T.get(u).high),r.T.get(u).index));
    	}
    	else
    	{
    		if(b==0)
    			return rest(r,r.T.get(u).low);
    		else
    			return rest(r,r.T.get(u).high);
    		
    	}
    }
    
    int satCount(Node u)
    {
    	if(u.index == x.length)
    	{
	    	if (u.terminalVal)
	    		res=1;
	    	else 
	    		res=0;
    	}
    	else
    	{
    		res = (int) ((Math.pow(2, (T.get(u.low).index - u.index - 1)) * satCount(T.get(u.low))) + (Math.pow(2, (T.get(u.high).index - u.index - 1)) * satCount(T.get(u.high))));
    	}
    	return res;
    }
    
    void anySat(Node u)
    {
    	if((u.index == anysat.length) && (!u.terminalVal))
    	{
    		System.out.println("Error");    	
    	}
    	else if((u.index == anysat.length) && (u.terminalVal))
    	{
    		for(int i =0;i<anysat.length;i++)
    		{
    			if(anysat[i] == -1)
    				anysat[i] = 0;
    				//anysat[i] = (int)(Math.ceil(Math.random()));
    			System.out.print(anysat[i]+ " ");
    		}
    		System.out.println();
    		return;
    	}
    	else if(u.low == 0)
    	{
    		anysat[u.index] = 1;
    		anySat(T.get(u.high));
    		
    	}
    	else
    	{
    		anysat[u.index] = 0;
    		anySat(T.get(u.low));
    	}
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
	public static void main(String[] args) 
	{
		System.out.println("Variable indices start from x0,x1....");
		System.out.println("Input expression: (or(equiv(x[0],x[1]),x[2])\n");
		//System.out.println("Input expression: (and(imp(not(x[0]),equiv(1,x[1])),not(x[1]))");
		//System.out.println("or(and(equiv(x[0],x[1]),equiv(x[2],x[3])),equiv(x[4],x[5])");
		BDD b = new BDD(3);
		
		long startTime = System.nanoTime();
		b.build(0);
		long stopTime = System.nanoTime();
		
		System.out.println("Table T after build:");
		System.out.println("Format : var(u),high(u),low(u)");
		for(int i=0;i<b.T.size();i++)
		{
			System.out.print("u ="+i+": " + (((b.T.get(i)).index)) + "," + ((b.T.get(i)).high) + "," + ((b.T.get(i)).low));
			System.out.println();
		}
		System.out.println("Execution time for build "+ (stopTime - startTime) + " ns");
		System.out.println("\n");
		
		startTime = System.nanoTime();
		b.satCount = (int)(Math.pow(2,(b.T.get(b.T.size()-1).index)) * b.satCount(b.T.get(b.T.size()-1)));
		stopTime = System.nanoTime();
		System.out.println("SatCount = " + b.satCount);
		System.out.println("Execution time for satCount "+ (stopTime - startTime) + " ns");
		System.out.println("\n");
		
		System.out.println("Anysat - Valid satisfying truth assignment: ");
		startTime = System.nanoTime();
		b.anySat(b.T.get(b.T.size()-1));
		stopTime = System.nanoTime();
		System.out.println("Execution time for anysat "+ (stopTime - startTime) + " ns");
		System.out.println("\n");
		
		BDD r = new BDD(3);
		int rs = 1;
		int vl = 0;
		System.out.println("Restrict constraints: x" +rs+ " / "+vl);
		startTime = System.nanoTime();
		r.restrict(b,rs,vl);
		stopTime = System.nanoTime();
		System.out.println("Execution time for restrict "+ (stopTime - startTime) + " ns");
	}
}
