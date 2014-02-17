/**
 * EvaluationScores.java
 *
 * Stores the training, test (real and predicted) scores/ratings to be used in
 * the evaluation of certain recommendation method.
 *
 * The training scores are read from a plain-text file with the following line
 * format: user_id, item_id, training_score.
 *
 * The test (real and predicted) scores are read from a plain-text file with the
 * following line format: user_id, item_id, real_score, predicted_score.
 *
 * @version 1.0 (November 11, 2013)
 *
 * @author Ivan Cantador, Universidad Autonoma de Madrid, ivan.cantador@uam.es
 */
package org.eswc2014.challenge.lodrecsys.evaluation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class EvaluationScores {

	private static final int SCORES_REAL = 1;
	private static final int SCORES_PREDICTED = 2;
//	private Map<String, Map<String, Score>> trainingScoreMap;
	private Map<String, Map<String, Score>> realTestScoreMap;
	private Map<String, Map<String, Score>> predictedTestScoreMap;
	private String mismatch = "";

	/**
	 * Builds an EvaluationScores instance storing the training and (real and
	 * predicted) test scores read from the files given as input arguments.
	 * 
	 * @param recommendationFile
	 *            the path/name of the file with the recommendation scores (user/item/rating)
	 * @param evaluationFile
	 *            the path/name of the file with the test scores to store (user/item/rating)
	 * 
	 * @throws IllegalArgumentException
	 *             in case of null or empty file(s)
	 */
	public EvaluationScores(String recommendationFile, String evaluationFile)
			throws Exception {
		if (recommendationFile == null) {
			throw new IllegalArgumentException("null recommendationFile ");
		}
		if (evaluationFile == null) {
			throw new IllegalArgumentException("null test score file name");
		}

		List<Score> realTestScores = this.readScoreFile(evaluationFile);
		if (realTestScores == null || realTestScores.isEmpty()) {
			throw new IllegalArgumentException("no real test scores");
		}

		List<Score> predictedTestScores = this
				.readScoreFile(recommendationFile);
		if (predictedTestScores == null || predictedTestScores.isEmpty()) {
			throw new IllegalArgumentException("no predicted test scores");
		}

		this.realTestScoreMap = new HashMap<String, Map<String, Score>>();
		for (int i = 0; i < realTestScores.size(); i++) {
			String userId = realTestScores.get(i).getUserId();
			String itemId = realTestScores.get(i).getItemId();
			Score trainingScore = realTestScores.get(i);

			if (!this.realTestScoreMap.containsKey(userId)) {
				this.realTestScoreMap.put(userId, new HashMap<String, Score>());
			}
			this.realTestScoreMap.get(userId).put(itemId, trainingScore);
		}

		this.predictedTestScoreMap = new HashMap<String, Map<String, Score>>();
		for (int i = 0; i < predictedTestScores.size(); i++) {
			String userId = predictedTestScores.get(i).getUserId();
			String itemId = predictedTestScores.get(i).getItemId();
			Score trainingScore = predictedTestScores.get(i);

			if (!this.predictedTestScoreMap.containsKey(userId)) {
				this.predictedTestScoreMap.put(userId,
						new HashMap<String, Score>());
			}
			this.predictedTestScoreMap.get(userId).put(itemId, trainingScore);
		}
	}

	private List<Score> readScoreFile(String file) throws Exception {
		return this.readScoreFile(file, SCORES_REAL);
	}

	private List<Score> readScoreFile(String file, int scoreType)
			throws Exception {
		if (scoreType != SCORES_REAL && scoreType != SCORES_PREDICTED) {
			throw new IllegalArgumentException("invalid score type");
		}

		ArrayList<Score> scores = new ArrayList<Score>();

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, ", \t");

			String userId = tokenizer.nextToken();
			String itemId = tokenizer.nextToken();
			double scoreValue = 0;
			if (scoreType == SCORES_REAL) {
				scoreValue = Double.valueOf(tokenizer.nextToken());
			} else if (scoreType == SCORES_PREDICTED) {
				Double.valueOf(tokenizer.nextToken());
				scoreValue = Double.valueOf(tokenizer.nextToken());
			}
			scores.add(new Score(userId, itemId, scoreValue));

		}
		reader.close();

		return scores;
	}

	/**
	 * Returns a map with the user-item-score tuples associated to the training
	 * scores.
	 * 
	 * @returns the user-item-score tuples in the training set
	 */
	public Map<String, Map<String, Score>> getTrainingScoreMap() {
//		return this.trainingScoreMap;
		return null;
	}

	/**
	 * Returns a map with the user-item-score tuples associated to the real test
	 * scores.
	 * 
	 * @returns the user-item-real_score tuples in the test set
	 */
	public Map<String, Map<String, Score>> getRealTestScoreMap() {
		return this.realTestScoreMap;
	}

	/**
	 * Returns a map with the user-item-score tuples associated to the training
	 * scores.
	 * 
	 * @returns the user-item-predicted_score tuples in the test set
	 */
	public Map<String, Map<String, Score>> getPredictedTestScoreMap() {
		return this.predictedTestScoreMap;
	}
	
	/**
	 * Return true if each couple user/item in realTestScoreMap is also present in predictedTestScoreMap.
	 * 
	 * @return boolean
	 */
	public boolean match() {
		Iterator<String> it = this.realTestScoreMap.keySet().iterator();
		String user;
		String item;
		Iterator<String> it2;
		while (it.hasNext()) {
			user = it.next();
			if (this.predictedTestScoreMap.containsKey(user)) {
				it2 = this.getRealTestScoreMap().get(user).keySet().iterator();
				while (it2.hasNext()) {
					item = it2.next();
					if (!this.predictedTestScoreMap.get(user).containsKey(item)) {
						mismatch += "Missing pair user " + user + " item " + item
								+ "<br>";
					}
				}
			} else {
				mismatch += "Missing user " + user + "<br>";
			}
		}
		return mismatch.equalsIgnoreCase("");
	}
	
	/**
	 * 
	 * @return string of the list of mismatches
	 */
	public String mismatch() {
		// TODO Auto-generated method stub
		return  mismatch;
	}

	public boolean checkNumRecs() {
		Iterator<String> it = this.realTestScoreMap.keySet().iterator();
		String user;
		while (it.hasNext()) {
			user = it.next();
			if (this.predictedTestScoreMap.containsKey(user)) {
				if(this.predictedTestScoreMap.get(user).size() < 20){
					mismatch += "For the user " + user + " the recommendations are less of 20.<br>";
				}
				
			} else {
				mismatch += "Missing user " + user + "<br>";
			}
		}
		return mismatch.equalsIgnoreCase("");
	}
}
