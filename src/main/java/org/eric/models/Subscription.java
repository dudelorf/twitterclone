package org.eric.models;

public class Subscription {

    private int subscriber_id;
    private int posterId;

	/**
	 * @return the subscriber_id
	 */
	public int getSubscriber_id() {
		return subscriber_id;
	}

	/**
	 * @param subscriber_id the subscriber_id to set
	 */
	public void setSubscriberId(int subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	/**
	 * @return the poster_id
	 */
	public int getPosterId() {
		return posterId;
	}

	/**
	 * @param posterId the poster_id to set
	 */
	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}
}