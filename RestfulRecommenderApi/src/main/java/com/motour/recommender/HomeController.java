package com.motour.recommender;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	

	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws TasteException 
	 * @throws IOException 
	 */
	//추천 목록 json으로 전달
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public String home(@PathVariable int id) throws IOException, TasteException{
		
		String str = mahoutRecommneder.getRecommenderItem(id);
		return str;
	}
	
}
	

