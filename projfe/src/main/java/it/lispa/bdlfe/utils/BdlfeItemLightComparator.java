package it.lispa.bdlfe.utils;

import it.lispa.bdlfe.dto.BdlfeItemLight;

import java.math.BigDecimal;
import java.util.Comparator;

public class BdlfeItemLightComparator implements Comparator<BdlfeItemLight>
{
	private BdlfeSearchOrderOptions compareMethod;

	@Override
	public int compare(BdlfeItemLight arg0, BdlfeItemLight arg1) {
		// TODO Auto-generated method stub
		if(compareMethod==null){
			compareMethod = BdlfeSearchOrderOptions.RELEVANCE;
		}
		// CompareTo torna 1 se arg0>arg1
		// CompareTo torna 0 se arg0=arg1
		// CompareTo torna -1 se arg0<arg1
		if(compareMethod.equals(BdlfeSearchOrderOptions.RELEVANCE)){
			/**
			 * Per evitare di cambiare la logica di comparazione con gli altri metodi sottostanti,
			 * switcho i due argomenti...
			 */
			BdlfeItemLight appoggio = arg0;
			arg0 = arg1;
			arg1 = appoggio;
			
			BigDecimal rel0 = null;
			BigDecimal rel1 = null;
			if(arg0.getRelevance()!=null && arg0.getRelevance()!=null){
				rel0 = arg0.getRelevance();
			}
			if(arg1.getRelevance()!=null && arg1.getRelevance()!=null){
				rel1 = arg1.getRelevance();
			}

			if(rel0==null && rel1==null){
				return 0;
			}
			if(rel0==null && rel1!=null){
				return -1;
			}
			if(rel0!=null && rel1==null){
				return 1;
			}
			
			return rel0.compareTo(rel1);
		}
		if(compareMethod.equals(BdlfeSearchOrderOptions.AO_AUTHOR)){
			String author0 = null;
			String author1 = null;
			if(arg0.getAuthors()!=null && arg0.getAuthors().get(0)!=null){
				author0 = arg0.getAuthors().get(0).getName();
			}
			if(arg1.getAuthors()!=null && arg1.getAuthors().get(0)!=null){
				author1 = arg1.getAuthors().get(0).getName();
			}

			if(author0==null && author1==null){
				return 0;
			}
			if(author0==null && author1!=null){
				return -1;
			}
			if(author0!=null && author1==null){
				return 1;
			}
			
			return author0.compareTo(author1);
		}
		if(compareMethod.equals(BdlfeSearchOrderOptions.AO_TITLE)){
			String title0 = null;
			String title1 = null;
			if(arg0.getTitle()!=null){
				title0 = arg0.getTitle();
			}
			if(arg1.getTitle()!=null){
				title1 = arg1.getTitle();
			}

			if(title0==null && title1==null){
				return 0;
			}
			if(title0==null && title1!=null){
				return -1;
			}
			if(title0!=null && title1==null){
				return 1;
			}
			return title0.compareTo(title1);
		}
		if(compareMethod.equals(BdlfeSearchOrderOptions.DATE)){
			String date0 = null;
			String date1 = null;
			if(arg0.getDate()!=null){
				date0 = arg0.getDate();
			}
			if(arg1.getDate()!=null){
				date1 = arg1.getDate();
			}

			if(date0==null && date1==null){
				return 0;
			}
			if(date0==null && date1!=null){
				return -1;
			}
			if(date0!=null && date1==null){
				return 1;
			}
			return date0.compareTo(date1);
		}
		return 0;
	}

	public BdlfeSearchOrderOptions getCompareMethod() {
		return compareMethod;
	}

	public void setCompareMethod(BdlfeSearchOrderOptions compareMethod) {
		this.compareMethod = compareMethod;
	}
}