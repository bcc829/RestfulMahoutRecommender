package com.motour.recommender;

import java.util.ArrayList;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

public class GenreRescorer implements IDRescorer {
	int UserId;
	ArrayList<String> userAttribute = new ArrayList<String>();

	public GenreRescorer(int id) {
		this.UserId = id;
		
		this.userAttribute = getUserAttributeInDB.returnPreferProperty(id);
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
			// �뀒�뒪�듃瑜� �쐞�빐 �듅�젙媛믪쓣 吏묒뼱�꽔�쓬
			Td = new TravelDestination(294439);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �젙遺� 3.0�뿉�꽌 媛��졇�삩 �뿬�뻾吏� �븘�씠�뵒�쓽 以� 遺꾨쪟

		String str = Td.getAttribute();

		for (int i = 0; i < this.userAttribute.size(); i++) {
			if (str.equals(this.userAttribute.get(i))) {
				// 媛�以묒튂 50�봽濡�
				return originalScore * 1.2;
			} 
		}
		return originalScore;
	}

}