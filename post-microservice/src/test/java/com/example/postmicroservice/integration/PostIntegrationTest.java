package com.example.postmicroservice.integration;

import com.example.postmicroservice.dummyMq.DummyReceiver;
import com.example.postmicroservice.model.Post;
import com.example.postmicroservice.mq.Sender;
import com.example.postmicroservice.repository.PostRepository;
import com.example.postmicroservice.service.PostService;
import com.google.common.io.Files;
import com.netflix.discovery.converters.Auto;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

//@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostIntegrationTest {

    @Value("${spring.rabbitmq.port}")
    private  String rabbitmqPort;

    @Autowired
    PostRepository postRepository;

    @Autowired
    DummyReceiver dummyReceiver;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PostService postService;


    //private List<Post> listPostsDummy;

//    @Before
//    public void initDummies(){
//        listPostsDummy = new ArrayList<>();
//
//        Post post1 = new Post();
//        post1.setId(1L);
//        post1.setTitle("This is Post 1 Title");
//        post1.setDescription("This is Post 1 Description");
//        postRepository.save(post1);
//        Post post2 = new Post();
//        post2.setId(2L);
//        post2.setTitle("This is Post 2 Title");
//        post2.setDescription("This is Post 2 Description");
//
//        listPostsDummy.add(post1);
//        listPostsDummy.add(post2);
//
//    }
    @ClassRule
    public static final ExternalResource resource = new ExternalResource() {
        private Broker broker = new Broker();
        @Override
        protected void before() throws Throwable {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File("src/test/resources/application-qa.properties")));
            String amqpPort = properties.getProperty("spring.rabbitmq.port");
            File tmpFolder = Files.createTempDir();
            String userDir = System.getProperty("user.dir").toString();
            File file = new File(userDir);
            String homePath = file.getAbsolutePath();
            BrokerOptions brokerOptions = new BrokerOptions();
            brokerOptions.setConfigProperty("qpid.work_dir", tmpFolder.getAbsolutePath());
            brokerOptions.setConfigProperty("qpid.amqp_port", amqpPort);
            brokerOptions.setConfigProperty("qpid.home_dir", homePath);
            brokerOptions.setInitialConfigurationLocation(homePath + "/src/test/resources/QpidConfig.json");
            broker.startup(brokerOptions);
        }

        @Override
        protected void after() {
            broker.shutdown();
        }
    };


//    @ClassRule
//    public static final ExternalResource resource = new ExternalResource() {
//        private Broker broker = new Broker();
//
//        @Override
//        protected void before() throws Throwable {
//            Properties properties = new Properties();
//            properties.load(new FileInputStream(new File("src/test/resources/application.properties")));
//            String amqpPort = properties.getProperty("spring.rabbitmq.port");
//            File tmpFolder = Files.createTempDir();
//            String userDir = System.getProperty("user.dir").toString();
//            File file = new File(userDir);
//            String homePath = file.getAbsolutePath();
//            BrokerOptions brokerOptions = new BrokerOptions();
//            brokerOptions.setConfigProperty("qpid.work_dir", tmpFolder.getAbsolutePath());
//            brokerOptions.setConfigProperty("qpid.amqp_port", amqpPort);
//            brokerOptions.setConfigProperty("qpid.home_dir", homePath);
//            brokerOptions.setInitialConfigurationLocation(homePath + "/src/test/resources/qpid-config.json");
//            broker.startup(brokerOptions);
//        }
//
//        @Override
//        protected void after() {
//            broker.shutdown();
//        }
//    };

    private Post createPost(){
        Post post = new Post();
        post.setId(1L);
        post.setTitle("This is Post 1 Title");
        post.setDescription("This is Post 1 Description");
        return post;
    }

    @Test
    public void receiveMsg_idSent_Success() throws InterruptedException {
        rabbitTemplate.convertSendAndReceive("post.comment", 1);
        Thread.sleep(5000);
        assertEquals(java.util.Optional.ofNullable(dummyReceiver.getId()), 1);

    }


    @Test
    @Transactional
    public void deletePost_ShouldDeleteComments_PostExist(){
        Post post = createPost();
        postRepository.save(post);
        System.out.println(post.getTitle());
        Post returnedPost = postRepository.findById(1L).orElse(null);
        System.out.println(returnedPost.getTitle());
        postRepository.deleteById(1L);
    }






}
