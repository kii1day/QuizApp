package bean;

import java.io.Serializable;
import java.util.ArrayList;

public class NekoDTO implements Serializable{
	  private ArrayList<NekoBean> quizList;
	  public NekoDTO(){
		  quizList = new ArrayList<NekoBean>();
	  }
	  public void add(NekoBean nb){
		  quizList.add(nb);
	  }
	  public NekoBean get(int i){
	    return quizList.get(i);
	  }
	  public int size(){
	    return quizList.size();
	  }
	}