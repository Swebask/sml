package sml;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import sml.SentiWordNet;

public class returnScore
{
 
    public List<String> adjectives=new ArrayList<String>();
    public List<String> adverbs=new ArrayList<String>();
    public double score;
  
    public returnScore()
    {
    	score=0.0;
    }
	
	public void identifyAdjAdv(String phrase)
	{
		StanfordCoreNLP pipeline = new StanfordCoreNLP();
	    Annotation annotation;
	    annotation = new Annotation(phrase);
	    pipeline.annotate(annotation);
	    List<CoreLabel> tokens= annotation.get(CoreAnnotations.TokensAnnotation.class);
	    CoreLabel token;
	    for (int i=0;i<tokens.size();i++) {
	    	token=tokens.get(i);
	    	 if(token.get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("RB")||token.get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("RBR")||token.get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("RBS"))
	    	 {	
	    		 if(tokens.get(i+1).get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("JJ")||tokens.get(i+1).get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("JJR")||tokens.get(i+1).get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("JJS"))
	    		 {
	    		    adverbs.add(token.get(CoreAnnotations.TextAnnotation.class).toString()+" "+tokens.get(i+1).get(CoreAnnotations.TextAnnotation.class).toString());
	    		    i++;
	    		 }
	    		 }
	    	 else
	        if(token.get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("JJ")||token.get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("JJR")||token.get(CoreAnnotations.PartOfSpeechAnnotation.class).toString().equals("JJS"))
	        	adjectives.add(token.get(CoreAnnotations.TextAnnotation.class).toString());
	        
	      }
	      List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	      Tree tree = sentences.get(0).get(TreeCoreAnnotations.TreeAnnotation.class);
	      PrintWriter out;
	      out = new PrintWriter(System.out);
	      tree.pennPrint(out);
	   // System.out.println(sentences.get(0).get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class).getChildrenWithReln()

	}
	
	public void adjPolarity() throws IOException
	{
		for(String adj : adjectives)
		{
			score+=SentiWordNet.extract(adj, "a");
		}
	}
	
	public void advPolarity() throws IOException
	{
		double advsc=0,adjsc=0;
		String StrongAff="astronomically, exceedingly, extremely, immensely, very, absolutely, certainly, exactly, totally";
		String WeakDoubt="barely, scarcely, weakly, slightly, possibly, roughly, apparently, seemingly";
		for(String adv : adverbs)
		{
			String[] advadj = adv.split(" ");	
			advsc=SentiWordNet.extract(advadj[0], "r");
			adjsc=SentiWordNet.extract(advadj[1], "a");
			if(StrongAff.contains(advadj[0]))
			{
				if(adjsc>0)
					score += adjsc + (1 - adjsc) * advsc;
				else if(adjsc<0)
					score += adjsc - (1 - adjsc) * advsc;
			}
			else
			if(WeakDoubt.contains(advadj[0]))
			{
				if(adjsc>0)
					score += adjsc - (1 - adjsc) * advsc;
				else if(adjsc<0)
					score += adjsc + (1 - adjsc) * advsc;
			}
			else score+=advsc+adjsc;
		}
	}

	public static double getScore(String phrase)
	{
		
		returnScore uspr=new returnScore();
		uspr.identifyAdjAdv(phrase);
		
		try {
			uspr.adjPolarity();
			uspr.advPolarity();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uspr.score;
	}
}