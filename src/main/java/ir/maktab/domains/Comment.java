package ir.maktab.domains;

import ir.maktab.base.domains.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "publisherid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "postid")
    private Post post;

    @Column(name = "comment", nullable = false)
    private String comment;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment: " +
                comment + '\t' +
                "user:" + user.getUserName() + "\t";

    }
}
