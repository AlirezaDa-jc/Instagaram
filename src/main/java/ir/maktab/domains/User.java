package ir.maktab.domains;

import ir.maktab.base.domains.BaseEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "username", nullable = false, unique = true)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "last_online")
    private Date date;
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new LinkedList<>();

    @ManyToMany
    @JoinTable(name = "User_Like",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")})
    Set<Post> postsLiked = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "flw_flwrs",
            joinColumns = {@JoinColumn(name = "flw_id")},
            inverseJoinColumns = {@JoinColumn(name = "flwr_id")})
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER)
    private Set<User> followings = new HashSet<>();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<Post> getPostsLiked() {
        return postsLiked;
    }

    public void setPostsLiked(Set<Post> postsLiked) {
        this.postsLiked = postsLiked;
    }

    public void addPostLiked(Post post) {
        postsLiked.add(post);
    }

    /*
    Ask!
     */
    public void addPostCommented(Comment comment) {
////        comment.setUser(this);
        comments.add(comment);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<User> followings) {
        this.followings = followings;
    }

    public void addFollower(User follower) {
        followers.add(follower);
        follower.getFollowings().add(this);
    }


    @Override
    public String toString() {
        return "User: \n" +
                "name: " + name + "\t" +
                "userName: " + userName + '\t' +
                "posts: " + posts.size() + '\t' +
                "Followers: " + followers.size() + '\t'+
                "Followings: " + followings.size() + '\t';
    }
}
