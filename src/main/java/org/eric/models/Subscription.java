package org.eric.models;

public class Subscription {

    private int subscriberId;
    private int posterId;

    /**
     * @return the subscriber_id
     */
    public int getSubscriberId() {
            return subscriberId;
    }

    /**
     * @param subscriber_id the subscriber_id to set
     */
    public void setSubscriberId(int subscriber_id) {
            this.subscriberId = subscriber_id;
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