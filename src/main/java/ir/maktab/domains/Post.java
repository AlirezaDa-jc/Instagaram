package ir.maktab.domains;

import ir.maktab.base.domains.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "date", nullable = false, updatable = false)
    private Date date = new Date();
    @ManyToOne
    @JoinColumn(name = "publisherid")
    private User user;

    @ManyToMany
    @JoinTable(name = "User_Like",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")})
    Set<User> likesUser = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getLikesUser() {
        return likesUser;
    }

    public void setLikesUser(Set<User> likes) {
        this.likesUser = likes;
    }

    public void addPostCommented(Comment comment) {
        comments.add(comment);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addLike(User userLiked) {
        likesUser.add(userLiked);
        userLiked.getPostsLiked().add(this);
    }

    public void addComment(Comment comment) {
//        comment.setPost(this);
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "Post: "
                + content + '\n' +
                "date: " + date +
                "\n User: " + user.getUserName() +
                "\n likes: " + likesUser.size()+
                "\n comments: " + comments.size() + "\t" + comments +
                '\n'+ '\n';
    }
}
