package realitycheck.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import realitycheck.model.Channel;
import realitycheck.model.Comment;

public interface CommentRepo extends CrudRepository<Comment, Integer>  {
	public List<Comment> findByVideoName(String videoName);
	public Comment findByContent(String content);
}
