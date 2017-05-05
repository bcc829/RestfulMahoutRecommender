package com.motour.recommender;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.eval.LoadEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class mahoutRecommneder {
	public static String getRecommenderItem(int id) throws IOException, TasteException {
		DataModel model = new FileDataModel(
				new File("/home/jeong/workspace/RestfulRecommenderApi/src/main/java/com/motour/recommender/ddd.csv"));

		UserSimilarity similarity = new EuclideanDistanceSimilarity(model);

		// new SpearmanCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0, similarity, model);
		// new NearestNUserNeighborhood(5,similarity,model);
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		LoadEvaluator.runLoad(recommender);

		// rescorer생성
		String json = "{" + "\"Items\"" + ":" + "[";
		IDRescorer testRescorer = new GenreRescorer(id);
		List<RecommendedItem> recommendations = recommender.recommend(id, 10, testRescorer);

		// String 파싱 - id 값만 찾음
		Iterator<RecommendedItem> itr = recommendations.iterator();
		while (itr.hasNext()) {
			RecommendedItem item = itr.next();
			String str = item.toString();
			if (itr.hasNext())
				json = json + "{" + "\"ID\"" + ":" + "\"" + str.substring(str.indexOf(":") + 1, str.indexOf(",")) + "\""
						+ "}" + ", ";
			else
				json = json + "{" + "\"ID\"" + ":" + "\"" + str.substring(str.indexOf(":") + 1, str.indexOf(",")) + "\""
						+ "}";
		}
		

		json = json + "]" + "}";
		return (json);
	}
}
