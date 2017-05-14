package com.motour.recommender;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.eval.LoadEvaluator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class mahoutRecommneder {
	public static String getRecommenderItem(int id) throws IOException, TasteException {
		//DB연동 
		MysqlDataSource datasource = new MysqlDataSource();
		datasource.setServerName("localhost");
		datasource.setUser("root");
		datasource.setPassword("465651");
		datasource.setDatabaseName("tourOfAll");
		
		DataModel model = new ReloadFromJDBCDataModel(new MySQLJDBCDataModel(datasource, "evaluation", "User_Id", "Place_Id", "Score", null));
		// DataModel model = new FileDataModel(
		// new
		// File("C:/Users/Administrator/git/RestfulMahoutRecommender/RestfulRecommenderApi/src/main/resources/ddd.csv"));
		
		//유사도 측정을 캐쉬로 저장
		UserSimilarity similarity = new CachingUserSimilarity(new EuclideanDistanceSimilarity(model),model);

		// new SpearmanCorrelationSimilarity(model);
		
		//유저 이웃 계산 결과를 캐쉬로 저장
		UserNeighborhood neighborhood = new CachingUserNeighborhood(new ThresholdUserNeighborhood(0, similarity, model),model);
		
		
		// new NearestNUserNeighborhood(5,similarity,model);
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		LoadEvaluator.runLoad(recommender);

		
		String json = "{" + "\"Items\"" + ":" + "[";
		//Rescorer 생성
		IDRescorer testRescorer = new GenreRescorer(id);
		List<RecommendedItem> recommendations = recommender.recommend(id, 10, testRescorer);

		// String Parsing 아이디값만 찾음
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
