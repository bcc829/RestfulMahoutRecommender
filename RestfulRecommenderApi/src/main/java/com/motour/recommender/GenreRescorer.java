package com.motour.recommender;

import java.util.ArrayList;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

import com.sun.tools.javac.util.List;

public class GenreRescorer implements IDRescorer {
	int UserId;
	ArrayList<String> userAttribute = new ArrayList<String>();

	public GenreRescorer(int id) {
		this.UserId = id;
		getUserAttributeInDB Attribute = new getUserAttributeInDB();
		this.userAttribute = Attribute.returnPreferProperty(id);
	}

	@Override
	public boolean isFiltered(long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double rescore(long ItemId, double originalScore) {
		TravelDestination Td = null;
		try {
			// 테스트를 위해 특정값을 집어넣음
			Td = new TravelDestination(294439);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 정부 3.0에서 가져온 여행지 아이디의 중 분류

		String str = Td.getAttribute();

		for (int i = 0; i < this.userAttribute.size(); i++) {
			if (str.equals(this.userAttribute.get(i))) {
				// 가중치 50프로
				System.out.println(Td.getAttribute() + "*1.5");
				return originalScore * 1.5;
			} 
		}
		return originalScore;
	}

}