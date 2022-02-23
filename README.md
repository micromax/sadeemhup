<p>

{

<b>Story :</b>

Back in 2016 I was working on a Multimedia project “iptv internet protocol television ” and suddenly media servers' monthly subscription licenses became super costy and we had to develop our own solutions to cut this cost .

<b>Problems :</b>

in such projects performance and protocols implementation was the most demanded requirement for its nature. We need to handle thousand requests every second per user for thousand users 24/7 services .

Also, we need to handle more than a thousand live video feeds from satellite broadcasters  .
And deliver it in real time to those users .

Also, we have to keep these servers running forever for our users .

Another problem to consider! The  bandwidth for those streams is about 35 ~ 40 GB . Most servers output was multicast packets . so UDP flood attack defending was daily mission

<b>Brainstorm & overthinking :</b>

Now I know the problem or most of it,
the solution have to be light footprint , with fault tolerance in design , efficient ,  secure and easy to develop

So we need a fast and well known stack with great concurrency functionality .

C/C++ is a super language that will do any job however it will be super hard to develop this complex project with a minimum team …

Dart .. is a permission language and i was considering it on this time however it was very young

Go ,, elegant like c i wish i knew it at that time .. if i go back in time i will select it :-)

Now we had to choose between  “java  , scala and kotlin”

While scala implements the Actor model on vm  witch i consider a great concurrency framework thats enable you to build a reactive server application however it’s fail on benchmark comparing to other jvm languages






You might notice that I will use jvm , what about other stacks  “C# , python , node.js …  etc  i will answer this later ”


Jvm is a good environment to develop such applications .
Now we need to define the technology stack like tools and framework … etc


In java world there are many frameworks for EE application like Spring Mvc , Spring Boot ,  Play framework and so on

However, in this project thairs more than CRUD & API concepts their is low level network protocol access like UDP / RTSP/RTMP/HLS/DASH/Websocket … etc

So we need to build it from ground with a b strong builtin HTTP server
At this point you will find java has ton of toolkits [ java MINA ,  java netty ] was my options i test both and the winner was “ netty   now on 2022 even play & spring use it to create their HTTP server  “
On my quist i found a great library with great features called vertx.io it’s built with Actor -like features called vertical , events bus , reactive programing and so on


we have another problem in such distributed systems
Fault tolerance , i will not use Erlang to active this concept, so i have to implement  a toolkit in jvm

Akka.io in 2016 this library wasn’t famous with very long documents to implement Actor model design .


Now that’s great i just need a templating engine to build the interface “I choose freemarker with no reason ”

Summary for that jvm languages will be used and any suitable builtin storages engine darby or any other external postgres or mysql , i pick 2 ORM’s to handle the data layer jooq orm and ebaens  ebean.io  every one of them has it’s dark side

So it’s simply verix.io for http serving , akka.io to handle heavy tasks   in actors design pattern
Freemarker templating engine  , ebaens as ORM

Super simple … right so after that decision i start developing a lightweight MVC java framework based on these tools   …



It’s need a lot of effort and contributions so you are welcome
}




</p>


create database I use Mysql use your own or even use H2 bout don't forget to add


your driver in Gradle file



<p><strong> Make your database Config correct  at this files</strong></p>


<li>/resources/application.properties</li>
<li>/resources/application.yaml</li>
<li>/resources/database.properties</li>


<p>

also, you need to  config same files in project root 'used when i need configuration outside the Jar  '
</p>
<li>application.properties</li>
<li>database.properties</li>


<p>First if you know OOP and MVC concepts you are good to go else {read about them first }</p>


<p> <strong>Code First </strong> </p>
<p> if need to go and build your database Like old school it's ok go and do it  </p>

<div> <strong> otherwise  under app.models  created file call Admin.java </strong> </div>


<code>


        package app.models;
        import javax.persistence.Entity;
        import javax.persistence.Id;
        import javax.persistence.Table;
        import java.sql.Timestamp;
    
        import io.ebean.Ebean;
        import io.ebean.Model;
        import io.ebean.annotation.Index;
        @Entity
        @Table(name="admin")
        public class Admin extends Model {
        
        
            @Id
            public long userId;
        
        
            @Index(unique=true)
            public String userName;
        
            public String userPassword;
        
            public Timestamp dateCreated;
        
            public String lastIP;
        
            public String token;
        
            public Timestamp lastLogin;
        
            public String getAPI_token() {
                return API_token;
            }
        
            public void setAPI_token(String API_token) {
                this.API_token = API_token;
            }
        
            public String API_token;
        
            public String getAPI_Key() {
                return API_Key;
            }
        
            public void setAPI_Key(String API_Key) {
                this.API_Key = API_Key;
            }
        
            public String API_Key;
        
            public Admin() {
                super();
            }
        
            public long getUserId() {
                return userId;
            }
        
            public void setUserId(Long userId) {
                this.userId = userId;
            }
        
            public String getUserName() {
                return userName;
            }
        
            public void setUserName(String userName) {
                this.userName = userName;
            }
        
            public String getUserPassword() {
                return userPassword;
            }
        
            public void setUserPassword(String userPassword) {
                this.userPassword = userPassword;
            }
        
            public Timestamp getDateCreated() {
                return dateCreated;
            }
        
            public void setDateCreated(Timestamp dateCreated) {
                this.dateCreated = dateCreated;
            }
        
            public String getLastIP() {
                return lastIP;
            }
        
            public void setLastIP(String lastIP) {
                this.lastIP = lastIP;
            }
        
            public String getToken() {
                return token;
            }
        
            public void setToken(String token) {
                this.token = token;
            }
        
            public Timestamp getLastLogin() {
                return lastLogin;
            }
        
            public void setLastLogin(Timestamp lastLogin) {
                this.lastLogin = lastLogin;
            }
        
            public Admin  finder(long id){
                return Ebean.find(Admin.class , id);
        
            }




</code>
<div><p>  your find this file and it's related files in repo code  </p>
</div>

<p> <strong> database migration  </strong> </p>
<p> if you didn't use Mysql go to DBmarge file and change database config to what you are using</p>

<p> than run DBmarge it will create migration file than restart your server you will see the table admin :-) </p>

<code style="background:#ccc">



        public class DBmarge {
           public static void main(String arg[]){
                try {
                    DbMigration dbMigration = DbMigration.create();
                    dbMigration.setPlatform(Platform.MYSQL); // change to your database type
                    dbMigration.generateMigration();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




</code>


<p> Models Helper and finder </p>
<div>


    in models we create 2 dir called 
    1 - logic // for CRUD helpers 
    2 - finder // to search and finde 



<p> in logic create Admin Helper by creating AdminHelper.java  </p>

<code>

      package app.models.logic;
      
      import app.models.Admin;
      import com.cloudsgen.system.core.DataBase.CGModel;
      import com.cloudsgen.system.core.DataBase.ICrud;
      import io.ebean.Ebean;
      import io.vertx.ext.web.RoutingContext;


        
      public class AdminHelper extends CGModel implements ICrud {
      
            //the constractor with RoutingContext
             public AdminHelper(RoutingContext rx)
                {
                    super(rx);
                }
                
      }


</code>

<p><strong> than we add CRUD method add , edit and delete</strong></p>
<code>

      @Override
          public void add(Object tClass) {
          if(tClass instanceof Admin)
          {
              Admin c = (Admin) tClass;
              c.save();
  
  
          }
      }
  
      @Override
      public void edit(Object id ) {
          if(id instanceof Admin) {
  
              Admin c = (Admin) id;
  
  
              c.update();
  
          }
      }
  
      @Override
      public void delete(Object id) {
  
  
              Admin c = Ebean.getDefaultServer().find(Admin.class).where().eq("user_id" , id).findOne();
             if(c != null) {
  
  
                 Ebean.getDefaultServer().delete(c);
             }
  
      }


</code>

<p> you will find the code in the Repo.. </p>



<p> <strong>now one More step </strong> we need a finder to search our data 
  you could use Ebean query 
  </p>

The code for that will be in "/models.finder"


</div>
<code>


    package app.models.finder;
    import app.models.Admin;
    import io.ebean.Ebean;
    import io.ebean.Finder;
    import java.util.List;
    
    public class AuthModel extends Finder<Long, Admin>  {
    
        private String Username;
        private String Password;
        private int isValid;
    
    
    
    
    
        public AuthModel(){
            super(Admin.class);
    
        }
        public Admin byId(long username)
        {
            return  query().where().eq("user_id" , username ).findOne();
        }
    
        public Admin byName(String username)
        {
            return  query().where().eq("user_name" , username ).findOne();
        }
    
    
        public Admin byNameAndPassword(String un , String pw) {
            return Ebean.getDefaultServer().find(Admin.class).where()
                    .eq("user_name", un)
                    .and()
                    .eq("user_password" , pw)
                    .findOne();
        }
    
    
        public Admin byIdAndTookenAndIp(long id , String token , String ip)  {
            return Ebean.getDefaultServer().find(Admin.class).where()
                    .eq("user_id", id)
                    .and()
                    .eq("token" , token)
                    .and()
                    .eq("last_ip" , ip)
                    .findOne();
        }
    
        public Admin byAPI( String token , String key) {
            return Ebean.getDefaultServer().find(Admin.class).where()
                    .eq("api_token", token)
                    .and()
                    .eq("api_key" , key)
                    .findOne();
        }
        public List<Admin> GetAll()
        {
            return  query().findList();
        }
        public int GetAllCount()
        {
            return  query().findList().size();
        }
    
    }


</code>


<p> controller and router : HowTo  </p>


create your controller in language you know Kotlin , java , Scala


    ps : scala controller should be in Scala Folder not in java 
    kotlin could be in same java class Folder 

<code> 




            public class HelloJava extends CG_Controller {
            
            
              public HelloJava(RoutingContext rxtx) {
                    super(rxtx);
                }
            
                /**
                 *   index : this default method for every controller
                 *   Render :  will Print any thing to response
                 *   simply this will print $String "Hello world ! from java"
                 *
                 * @return void
                 * @throws IOException
                 */
                @Override
                public void index() throws IOException
                {
                    super.index();
                    Render("Hello world ! from java");
                }
            
            }

</code> 

<p>
 this very basic controller 
 we have to add it to our router File 
 like this 

 <code> 
    Hellojava = app.controller.HelloJava
 </code>
</p>


<p>

in app.controller.HelloJava you will find how to Render View and pass data to it

how to USE GET , POST  , REQUEST and Signal Methods  to get users input :









</p>

<p><strong> this.input.POST("var_name"); </strong> its take String Parameter with name in query   </p>
<p><strong> this.input.GET("var_name"); </strong> its take String Parameter with name in query   </p>
<p><strong> this.input.REQUEST("var_name"); </strong> its take String Parameter with name in query  work with GET and POST requites   </p>
<p><strong> this.input.Signal(1); </strong> its take Integer Parameter with offset for requites  path like /controller/metho/data1/data2   </p>

<p><strong>this.input.setCookie("Cookie_name", "Cookie_val"); </strong>set Cookie 2 Parameter , Key_name and Value </p>
<p><strong>this.input.GetCookie("Cookie_name"); </strong> Get Cookie 1 Parameter , Key_name return Value   </p>


<p>Redirect("path/to/redirect"); this method will Redirect user to a path of your chaise  </p>



<p> <strong>annotation for Auth and Preloading </strong> </p>

<p> we have built-in annotation parser for authenticate some action like user requites and  ... etc    </p>


you will find example called AdminAuth in models/logic

<code>



    package app.models.logic;
    
    import app.models.Admin;
    import app.models.finder.AuthModel;
    import com.cloudsgen.system.core.DataBase.CGModel;
    import io.vertx.ext.web.RoutingContext;
    
    public class AdminAuth  extends CGModel {
    
    
    
    
    
    
        public AdminAuth(RoutingContext rx) {
            super(rx);
            String Tokin =  this.input.GetCookie("_cgmain");
            String UUID =  this.input.GetCookie("_cgalphas");
    
            String UserIp = this.input.getAgentIp();
            AuthModel authModel  = new AuthModel();
    
            Admin admin = null;
            if(Tokin == null || UUID == null)
            {
                this.setLook(true);
                Redirect("login");
            }else {
    
    
                long lid = 0;
    
                try {
                    lid = Long.parseLong(UUID);
                }catch (NumberFormatException ex)
                {
    
                   lid = 0 ;
                }
    
                try {
                    admin = authModel.byIdAndTookenAndIp(lid , Tokin , UserIp);
                    if(admin == null)
                    {
    
    
                        Redirect("login");
                    }else
                    {
                        this.setLook(false);
    
                    }
                }catch ( ExceptionInInitializerError e)
                {
                    Redirect("syserrors/misconf");
                }
    
    
            }
    
    
        }
    
    
    
    
    
    
    
    
    
    }



</code>



Ok how to use to Auth any controller Simply like that "before controller class deceleration " or before method

<code>

    @Iauthentication(AuthModel = AdminAuth.class )
    public class Administration  extends CG_Controller {



        public Administration(RoutingContext rxtx) {
            super(rxtx);
        }



        @Override
        public void index() throws IOException {
        }
    }
</code>



<p> Views : HowTo  </p>

<li> we use Freemarker as template engine    </li>
view located in /resources/views 

static files served from /resources/webroot


called in view like that


<code>

    <link href="/static/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
</code>



<code>




view's part is

0 - /views/
1  - /views/layout/
2 - /views/partia/

</code>

go to view and discover it and read Freemarker documentation for Var , Lists , Array , Loop
it's literally will take no time to master it


I hope this Help some one need fast and high performance APP
the powerful point of this Work "The Built in Web server "

<code>
TODO : add dashboard to control from it
add more tutorial and library 

</code>

finally feel free to contact me
<p><strong>  <a href="https://www.linkedin.com/in/mohamed-alaa-05a54646/">linkedin</a></strong></p>
<p><strong>  <a href="mailto:m.elqrwash@gmail.com">m.elqrwash@gmail.com</a></strong></p>
<p><strong>  <a href="mailto:info@sadeem-egypt.com">info@sadeem-egypt.com</a></strong></p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><strong>stay tuned&nbsp;</strong></p>
<p>&nbsp;</p>