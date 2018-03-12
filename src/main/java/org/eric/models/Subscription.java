package org.eric.models;

public class Subscription {

    private int subscriber_id;
    private int poster_id;

	/**
	 * @return the subscriber_id
	 */
	public int getSubscriber_id() {
		return subscriber_id;
	}

	/**
	 * @param subscriber_id the subscriber_id to set
	 */
	public void setSubscriber_id(int subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	/**
	 * @return the poster_id
	 */
	public int getPoster_id() {
		return poster_id;
	}

	/**
	 * @param poster_id the poster_id to set
	 */
	public void setPoster_id(int poster_id) {
		this.poster_id = poster_id;
	}
}