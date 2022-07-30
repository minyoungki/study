package com.example.beginner.service;

import com.example.beginner.dto.PostRequestDto;
import com.example.beginner.dto.ResponseDto;
import com.example.beginner.entity.Post;
import com.example.beginner.repository.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor  //lombok에 final 생성자를 대신 만들어 준다.
@Service
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public ResponseDto<?> createPost(PostRequestDto requestDto) {
    // 데이터 타입 ResponseDto<?>으로 PostRequestDto requestDto를 받는다.

    Post post = new Post(requestDto);
    //새로운 requestDto이란 매개변수를 받는 인스턴스 post를 생성

    postRepository.save(post);
    //postRepository 에 post를 저장

    return ResponseDto.success(post);
    //ResponseDto에 success에 post를 반환
  }

  @Transactional(readOnly = true)  //readOnly는 의도지 않게 데이터를 변경하는 것을 막주고, 속도를 향상시켜준다.
  public ResponseDto<?> getPost(Long id) {
    // 데이터 타입 ResponseDto<?>으로 Long id를 받는다.

    Optional<Post> optionalPost = postRepository.findById(id);
    //데이터타입 Optional<Post> 인 optionalPost에 postRepository에서 id를 찾아 너어 준다.

    if (optionalPost.isEmpty()) {
      return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
    }
    // 만약 optionalPost가 isEmpty() = 비어 있다면 ResponseDto에 fail에 값을 전달.

    return ResponseDto.success(optionalPost.get());
    // 아니라면 ResponseDto에 success에 optionalPost.get()을 전달
  }

  @Transactional(readOnly = true)
  public ResponseDto<?> getAllPost() {
    return ResponseDto.success(postRepository.findAllByOrderByModifiedAtDesc());
  }
  // 데이터 타입 ResponseDto<?>으로  getAllPost()클래스 생성.
  // 호출되면 ResponseDto.success에 postRepository.findAllByOrderByModifiedAtDesc()를 보낸다.

  @Transactional
  public ResponseDto<Post> updatePost(Long id, PostRequestDto requestDto) {
    // 데이터 타입 ResponseDto<?>으로 매개변수 Long id, PostRequestDto requestDto를 받는다.

    Optional<Post> optionalPost = postRepository.findById(id);
    //데이터타입 Optional<Post> 인 optionalPost에 postRepository에서 id를 찾아 너어 준다.

    if (optionalPost.isEmpty()) {
      return ResponseDto.fail("NULL_POST_ID", "post id isn't exist");
    }
    // 만약 optionalPost가 isEmpty() = 비어 있다면 ResponseDto에 fail에 값을 전달.

    Post post = optionalPost.get();  //아니라면 optionalPost를 post에 넣음
    post.update(requestDto);        //update 실행

    return ResponseDto.success(post);
    // 아니라면 ResponseDto에 success에 post을 전달
  }

  @Transactional
  public ResponseDto<?> deletePost(Long id) {  //위랑 거의 동일
    Optional<Post> optionalPost = postRepository.findById(id);

    if (optionalPost.isEmpty()) {
      return ResponseDto.fail("NOT_FOUND", "post id is not exist");
    }

    Post post = optionalPost.get();

    postRepository.delete(post);

    return ResponseDto.success(true);
  }

  @Transactional(readOnly = true)
  public ResponseDto<?> validateAuthorByPassword(Long id, String password) {
    Optional<Post> optionalPost = postRepository.findById(id);

    if (optionalPost.isEmpty()) {
      return ResponseDto.fail("NOT_FOUND", "post id is not exist");
    }

    Post post = optionalPost.get();
    //Long id, String password를 받아온다.

    if (post.getPassword().equals(password)) {  //getPassword()를 부르지도 않았는데 왜 사용가능한가?
      return ResponseDto.fail("PASSWORD_NOT_CORRECT", "password is not correct");
    }

    return ResponseDto.success(true);
  }


}
