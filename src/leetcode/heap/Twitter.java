package leetcode.heap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname Twitter
 * @Date 2020/8/8 2:10
 * @Autor lengzefu
 */
public class Twitter {
    //用于记录每条推文的时间戳
    int count = 0;
    //记录所有的用户，推文的信息已经存储在用户信息中
    HashMap<Integer, User> userMap = new HashMap<>();

    /**
     * 用户类
     */
    class User {
        int userId;
        //用户关注人列表
        Set<Integer> follows;
        //用户所发的最新的推文
        Tweet latestTweet;

        public User(int userId) {
            this.userId = userId;
            follows = new HashSet<>();
            //默认关注自己
            follows.add(userId);
            this.latestTweet = null;
        }

        public int getUserId() {
            return userId;
        }

        /**
         * 发推文
         * @param tweetId
         */
        public void postTweet(int tweetId) {
            count++;
            Tweet newTweet = new Tweet(tweetId, count);
            newTweet.nextTweet = latestTweet;
            latestTweet = newTweet;
        }

        /**
         * 关注
         * @param followeeId
         */
        public void follow (int followeeId) {
           follows.add(followeeId);
        }

        /**
         * 取关
         * @param followeeId
         */
        public void unfollow(int followeeId) {
            if(this.userId != followeeId) {
                follows.remove(followeeId);
            }
        }
    }

    /**
     * 推文类
     */
    class Tweet {
        int tweetId;
        int timestamp;
        Tweet nextTweet;

        public Tweet(int tweetId, int timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;
            this.nextTweet = null;
        }
    }

    public Twitter() {

    }

    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
        }
        User currentUser = userMap.get(userId);
        currentUser.postTweet(tweetId);

    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        if (!userMap.containsKey(userId)) return res;
        //使用一个优先队列，按照时间戳由大到小给推文排序，选取最大的10个
        PriorityQueue<Tweet> queue = new PriorityQueue<>(userMap.size(), (a, b) -> (b.timestamp - a.timestamp));
        // 关注列表的用户 Id
        Set<Integer> usersId = userMap.get(userId).follows;
        //先将所有的链表节点插入优先队列
        for(int u : usersId) {
            if(userMap.get(u).latestTweet == null) continue;
            queue.add(userMap.get(u).latestTweet);
        }
        //然后逐个出队列
        while (!queue.isEmpty()) {
            if(res.size() == 10) break;
            Tweet tweet = queue.poll();
            res.add(tweet.tweetId);
            if(tweet.nextTweet != null) {
                queue.add(tweet.nextTweet);
            }
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)) {
            userMap.put(followerId, new User(followerId));
        }
        if(!userMap.containsKey(followeeId)) {
            userMap.put(followeeId, new User(followeeId));
        }
        User currentUser = userMap.get(followerId);
        currentUser.follow(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId)) {
            User currentUser = userMap.get(followerId);
            currentUser.unfollow(followeeId);
        }
    }

    public static void main(String[] args) {
        List<Integer> res = new ArrayList<>();
        Twitter twitter = new Twitter();
        //twitter.postTweet(1, 5);
        res = twitter.getNewsFeed(1);
        res.forEach(r -> System.out.print("[" + r + "]"));
/*        System.out.println("\n");
        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        res = twitter.getNewsFeed(1);
        res.forEach(r -> System.out.print("[" + r + "]"));
        System.out.println("\n");
        twitter.unfollow(1, 2);
        res = twitter.getNewsFeed(1);
        res.forEach(r -> System.out.print("[" + r + "]"));
        System.out.println("\n");*/
    }
}
