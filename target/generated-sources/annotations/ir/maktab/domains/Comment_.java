package ir.maktab.domains;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ extends ir.maktab.base.domains.BaseEntity_ {

	public static volatile SingularAttribute<Comment, Post> post;
	public static volatile SingularAttribute<Comment, String> comment;
	public static volatile SingularAttribute<Comment, User> user;

}

