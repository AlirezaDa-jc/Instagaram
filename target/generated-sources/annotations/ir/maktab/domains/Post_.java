package ir.maktab.domains;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Post.class)
public abstract class Post_ extends ir.maktab.base.domains.BaseEntity_ {

	public static volatile SingularAttribute<Post, Date> date;
	public static volatile SingularAttribute<Post, byte[]> image;
	public static volatile ListAttribute<Post, Comment> comments;
	public static volatile SingularAttribute<Post, byte[]> video;
	public static volatile SetAttribute<Post, User> likesUser;
	public static volatile SingularAttribute<Post, User> user;
	public static volatile SingularAttribute<Post, String> content;

}

