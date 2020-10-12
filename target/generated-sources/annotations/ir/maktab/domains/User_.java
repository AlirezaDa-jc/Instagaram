package ir.maktab.domains;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends ir.maktab.base.domains.BaseEntity_ {

	public static volatile SingularAttribute<User, Date> date;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SetAttribute<User, Comment> comments;
	public static volatile SetAttribute<User, User> followers;
	public static volatile SetAttribute<User, User> followings;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile ListAttribute<User, Post> posts;
	public static volatile SetAttribute<User, Post> postsLiked;

}

