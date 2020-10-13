package ir.maktab.domains;

import ir.maktab.base.domains.BaseEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity<Long> {
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "date", nullable = false, updatable = false)
    private Date date = new Date();
    @Lob
    @Column(name="image", columnDefinition="mediumblob")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToMany
    @JoinTable(name = "User_Like",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")})
    Set<User> likesUser = new HashSet<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> comments = new LinkedList<>();


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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
