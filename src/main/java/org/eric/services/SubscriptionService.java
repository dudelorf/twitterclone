package org.eric.services;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.eric.models.Subscription;

public class SubscriptionService extends BaseService{

    public SubscriptionService(BasicDataSource datasource){
        super(datasource);
    }

    /**
     * Gets all subscriptions for the subscriber
     * 
     * @param subscriberId
     */
    public List<Subscription> getSubscriptions(int subscriberId){
        ResultSetHandler<List<Subscription>> handler = new BeanListHandler<>(Subscription.class);
        
        String sql = "SELECT * " 
                   + "FROM subscriptions "
                   + "WHERE subscriber_id = ?";

        return query(handler, sql, subscriberId);
    }

    public boolean addSubscription(int subscriberId, int posterId){
        String sql = "INSERT INTO subscriptions "
                   + "( "
                   + " subscriber_id, "
                   + " poster_id "
                   + ") "
                   + " VALUES "
                   + "(?, ?)";

        return update(sql, subscriberId,
                           posterId
                     ) != -1;
    }

    public boolean removeSubscription(int subscriberId, int posterId){
        String sql = "DELETE FROM subscriptions "
                   + "WHERE subscriber_id = ? "
                   + "AND poster_id = ?";
                   
        return update(sql, subscriberId,
                           posterId
                     ) != -1;
    }
}