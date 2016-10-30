// The "Keys" class.
import java.io.*;
public class Keys
{
    public int data[];
    private int numOfKeys;
    private String labels[];
    private String file;
    
    public Keys(String f, int d)
    {
	numOfKeys = d;
	data   = new int[numOfKeys];
	labels = new String[numOfKeys];
	
	try
	{
	    file = f;
	    FileReader fr = new FileReader (file);
	    BufferedReader br = new BufferedReader (fr);
	    int i = 0;
	    String s = br.readLine();
	    while( (s != null)&&(i < numOfKeys*2) ) //If there is still data in file, or if enough data was leeched out
	    {
		if(i < numOfKeys)
		{
		    data[i] = Integer.parseInt(s);
		}
		else
		{
		    labels[i-numOfKeys] = s;
		}
		i++;
		s = br.readLine();
	    }
	    br.close();
	}
	catch(Exception e){System.out.println("Exception in Keys::Keys(): " + e.toString());};
    }
    
    public void setKey(int keyIndex, int changeTo)
    {
	if( (keyIndex >= 0) && (keyIndex < numOfKeys) )
	{
	    data[keyIndex] = changeTo;
	}
	this.save();
    }
    public int getKey(int keyIndex)
    {
	if((keyIndex >= 0) && (keyIndex < numOfKeys))
	{
	    return data[keyIndex];
	}
	else return -1;
    }
    
    public void save()
    {
	try
	{
	    FileWriter fw = new FileWriter (file);
	    PrintWriter pw = new PrintWriter (fw);
	    for(int i = 0; i < numOfKeys; i++)
	    {
		pw.println(data[i]);
	    }
	    for(int i = 0; i < numOfKeys; i++)
	    {
		pw.println(labels[i]);
	    }
	    pw.close();
	}
	catch(Exception e){System.out.println("Exception in Keys::Save(): " + e.toString());};
    }
    public int check(int keyCode)
    {
	for(int i = 0; i < numOfKeys; i++)
	{
	    if(data[i] == keyCode)
	    {
		return i;
	    }
	}
	return -1;
    }
    public String getLabel(int keyIndex)
    {
	if((keyIndex >= 0) && (keyIndex < numOfKeys))
	{
	    return labels[keyIndex];
	}
	else
	{
	    return null;
	}
    }
    
} // Keys class
