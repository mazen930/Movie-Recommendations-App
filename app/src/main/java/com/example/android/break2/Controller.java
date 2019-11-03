package com.example.android.break2;
import android.app.DownloadManager;
import android.widget.Switch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Controller {
    private DBManager dbMan; // A Reference of type DBManager

    public Controller() {
        dbMan = new DBManager(); // Create the DBManager Object
    }
    public void TerminateConnection() {
       dbMan.closeConnection();
    }

    public int[] InsertNewFollower(String name, int age, String email, String password, String username) throws SQLException {
        int ResultArr[] = new int[2];
        // query for checking this username is used before or not
        String username_queryCheck = "Select * from follower,admins where follower.username ='" + username + "' or admins.username ='" + username + "' ;";
        ResultSet username_RS = (ResultSet) dbMan.ExecuteReader(username_queryCheck);
        if (username_RS.next()) {
            ResultArr[0] = -1;
            ResultArr[1] = -1;
            return ResultArr;// -1 ARRAY for username used before
        }
        // query for checking this email is used before or not
        String email_queryCheck = "Select * from follower,admins where follower.email ='" + email + "' or admins.email ='" + email + "';";
        ResultSet emailcheck_RS = (ResultSet) dbMan.ExecuteReader(email_queryCheck);
        if (emailcheck_RS.next()) {

            ResultArr[0] = -100;
            ResultArr[1] = -100;
            return ResultArr;// -100 ARRAY for email used before
        }
        // otherwise the username and the email are valid so we can enter the query now
        String query = "Insert into follower (name,age,Email,fpassword,username) values( " +
                "'" + name + "'," +
                age + "," +
                "'" + email + "'," +
                "'" + password + "'," +
                "'" + username + "' ) ; ";

        int res = dbMan.ExecuteNonQuery(query);
        if (res == 1) {
            query = "Select * from follower " + "" +
                    "where " +
                    " ( follower.username= '" + username + "') and follower.fpassword = '" + password + "';";
            ResultSet UserData = dbMan.ExecuteReader(query);
            if (UserData.next()) {
                ResultArr[0] = 2;  // the first element is for the privileges  2 means it is follower
                ResultArr[1] = UserData.getInt("follower_id");  // the second element is for the user ID
                return ResultArr;
            }
        }
        return null;
    }
    public int InsertNewAdmin(String username, String email, String password) throws SQLException {
        // query for checking this username is used before or not
        String username_queryCheck = "Select * from follower,admins where follower.username ='" + username + "' or admins.username ='" + username + "' ;";
        ResultSet username_RS = (ResultSet) dbMan.ExecuteReader(username_queryCheck);
        if (username_RS.next()) {
            return -1; // if it is used the function returns -1 for username used error
        }
        // otherwise the username is valid so we can enter the query now
        String query = "Insert into admins (username,email,a_password) values( " +
                "'" + username + "'," +
                "'" + email + "'," +
                "'" + password + "' ) ; ";
        return dbMan.ExecuteNonQuery(query);
    }


    public int[] LoginCheck(String username, String password) throws SQLException {
        String query = "Select * from follower " + "" +
                "where " +
                " ( follower.username= '" + username + "') and follower.fpassword = '" + password + "';";
        ResultSet isUser = dbMan.ExecuteReader(query);
        int ResultArr[] = new int[2];
        if (isUser.next()) {
            ResultArr[0] = 2;  // the first element is for the privileges  2 means it is follower
            ResultArr[1] = isUser.getInt("follower_id");  // the second element is for the user ID
            return ResultArr;
        }
        query = "Select * from admins " + "" +
                "where " +
                " ( admins.username= '" + username + "' ) and a_password = '" + password + "';";
        ResultSet isAdmin = dbMan.ExecuteReader(query);
        if (isAdmin.next()) {
            ResultArr[0] = 1;  // the first element is for the privileges 1 means it is an admin
            ResultArr[1] = isAdmin.getInt("admin_id"); // the second element is for the user ID
            return ResultArr;
        } else
            return null;
    }
    public int InsertAdv(String title, String cmpName, String stDate,
                         String endDate, String content, String price, String url, int adminID)throws SQLException
    {
        // here we need to pickup a query that contains the current logged admin id
        String checkquery = "select * from advertisement where ad_title = '" + title + "'  ;";
        ResultSet adv_exists = dbMan.ExecuteReader(checkquery);
        if (adv_exists.next())
            return 404; // returns 404 if the actor is already exist with same name
        String query = "Insert into advertisement (company_name,ad_title,ad_content,ad_start_date,ad_end_date,price,url,admin_id) values( " +
                "'" + cmpName + "'," +
                "'" + title + "'," +
                "'" + content + "'," +
                "'" + stDate + "'," +
                "'" + endDate + "'," +
                +Integer.parseInt(price) + "," +
                "'" + url + "'," + adminID + ");";
        return dbMan.ExecuteNonQuery(query);
    }
    public int InsertNewCinema(String cname, String clocation) throws SQLException {
        String checkquery = "select * from cinema where cinema_name = '" + cname + "'  ;";
        ResultSet cinema_exists = dbMan.ExecuteReader(checkquery);
        if (cinema_exists.next())
            return 404; // returns 404 if the actor is already exist with same name
        String query = "Insert into cinema (cinema_name,cinema_location) values( " +
                "'" + cname + "'," +
                "'" + clocation + "');";
        return dbMan.ExecuteNonQuery(query);
    }

    public int InsertNewMovie(String name, String language,
                              String rdate,String description,
                              int age_rest,String url,
                              Float reviewers,Float users,
                              int directorID,int authorID,
                              String duration,String genre)
    {

        return 1;
    }
    public ResultSet GetMoviesNames()
    {
         String query="select * from movies ";
         return dbMan.ExecuteReader(query);
    }
    public ResultSet GetMemberNames(int type)
    {
        String kind=Integer.toString(type);
        String query="select * from crew_members where member_type="+kind+";";
        return dbMan.ExecuteReader(query);
    }

    public ResultSet GetCinemaNames()
    {
        String query="select * from cinema ";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet GetMovieInfo(String Name)
    {
        String query="select * from movies where movie_name like '%"+Name+"%';";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet GetMemberInfo(String Name)
    {
        String query="select * from crew_members where member_name like '%"+Name+"%';";
        return dbMan.ExecuteReader(query);
    }

    public ResultSet GetCinemaInfo(String Name)
    {
        String query="select * from cinema where cinema_name='"+Name+"';";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet GetAuthorName(int id)
    {
        String query="select member_name from movies,crew_members where authors_id=member_id and authors_id="+id+";";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet GetDirectorName(int id)
    {
        String query="select member_name from movies,crew_members where director_id=member_id and director_id="+id+";";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet GetActorName(int id)
    {
        String query="select member_name from crew_members c ,actors_movie a where a.movie_id="+id+" and a.actors_id=c.member_id ";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet SelectMovieByName(String name)
    {
        String query = "select * from movies where movie_name = '" + name + "';";
        return dbMan.ExecuteReader(query);
    }
    public int AddMovieToCinema(String cname,String mname,String showtime,int price)throws SQLException
    {
        ResultSet cinemaData =SelectCinemaByName(cname);
        ResultSet movieData = SelectMovieByName(mname);
        int cinemaID,movieID;
        if (cinemaData.next()) {
            cinemaID = cinemaData.getInt("cinema_id");
        } else return -1;
        if(movieData.next())
            movieID=movieData.getInt("movie_id");
        else return -1;

        String query = "Insert into available_at values (" + cinemaID + "," + movieID +", '"+showtime+"',"+price+");";
        return dbMan.ExecuteNonQuery(query);

    }
    public int InsertActor(String ActorName, String ActorBirthDay, String brief) throws SQLException {

        //    String checkquery ="select * from crew_members where member_name = '"+ActorName+"' and birth_date="+"'"+ ActorBirthDay+"' ;";
        String checkquery = "select * from crew_members where member_name = '" + ActorName + "'  ;";
        ResultSet actor_exists = dbMan.ExecuteReader(checkquery);
        if (actor_exists.next())
            return 404; // returns 404 if the actor is already exist with same name

        String query = "Insert into crew_members(member_name,birth_date,brief,member_type)" +
                " values('" + ActorName + "'," +
                "'" + ActorBirthDay + "'," +
                "'" + brief + "',0);"; //Zero for actor tpyes
        int res = dbMan.ExecuteNonQuery(query);
        if (res == 1) {
            query = "select * from crew_members where member_name = '" + ActorName + "' and birth_date=" + "'" + ActorBirthDay + "' ;";
            ResultSet actor = dbMan.ExecuteReader(query);
            int actorID;
            if (actor.next()) {
                actorID = actor.getInt("member_id");
            } else return -1;

            query = "Insert into actors values (" + actorID + ");";
            return dbMan.ExecuteNonQuery(query);
        } else return -1;
    }

    public int InsertDirector(String Name, String BirthDay, String Brief, String Style) throws SQLException {
        //        String checkquery ="select * from crew_members where member_name = '"+Name+"' and birth_date="+"'"+ BirthDay+"' ;";
        String checkquery = "select * from crew_members where member_name = '" + Name + "' ;";
        ResultSet actor_exists = dbMan.ExecuteReader(checkquery);
        if (actor_exists.next())
            return 404; // returns 404 if this crew_member is already exist with same name **

        String query = "Insert into crew_members(member_name,birth_date,brief,member_type)" +
                " values('" + Name + "'," +
                "'" + BirthDay + "'," +
                "'" + Brief + "',1);"; //ONE for Director tpyes
        int res = dbMan.ExecuteNonQuery(query);
        if (res == 1) {
            query = "select * from crew_members where member_name = '" + Name + "' and birth_date=" + "'" + BirthDay + "' ;";
            ResultSet crew_memb = dbMan.ExecuteReader(query);
            int memberID;
            if (crew_memb.next()) {
                memberID = crew_memb.getInt("member_id");
            } else return -1;

            query = "Insert into director values (" + memberID + ",'" + Style + "');";
            return dbMan.ExecuteNonQuery(query);
        } else return -1;
    }

    public int InsertAuthor(String Name, String BirthDay, String Brief, String Style) throws SQLException {
        //        String checkquery ="select * from crew_members where member_name = '"+Name+"' and birth_date="+"'"+ BirthDay+"' ;";
        String checkquery = "select * from crew_members where member_name = '" + Name + "' ;";
        ResultSet actor_exists = dbMan.ExecuteReader(checkquery);
        if (actor_exists.next())
            return 404; // returns 404 if this crew_member is already exist with same name ** **

        String query = "Insert into crew_members(member_name,birth_date,brief,member_type)" +
                " values('" + Name + "'," +
                "'" + BirthDay + "'," +
                "'" + Brief + "',2);"; //TWO for Director tpyes
        int res = dbMan.ExecuteNonQuery(query);
        if (res == 1) {
            query = "select * from crew_members where member_name = '" + Name + "' and birth_date=" + "'" + BirthDay + "' ;";
            ResultSet crew_memb = dbMan.ExecuteReader(query);
            int memberID;
            if (crew_memb.next()) {
                memberID = crew_memb.getInt("member_id");
            } else return -1;

            query = "Insert into authors values (" + memberID + ",'" + Style + "');";
            return dbMan.ExecuteNonQuery(query);
        } else return -1;
    }

    public ResultSet SelectAllCrewMembers() throws SQLException {
        String query = "select * from crew_members;"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);

    }

    public ResultSet SelectAllMovies() throws SQLException {
        String query = "select * from movies;"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);
    }

    public ResultSet SelectAllAdvs() throws SQLException {
        String query = "select * from advertisement;"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);

    }

    public ResultSet SelectAllActors() throws SQLException
    { String query = "select * from crew_members,actors where member_id=actors_id ;"; //
        return dbMan.ExecuteReader(query);
    }

    public ResultSet SelectAllDirectors() throws SQLException
    { String query = "select * from crew_members,director where member_id=director_id ;"; //
        return dbMan.ExecuteReader(query);
    }

    public ResultSet SelectAllAuthors() throws SQLException
    { String query = "select * from crew_members,authors where member_id=authors_id ;"; //
        return dbMan.ExecuteReader(query);
    }

    public ResultSet SelectCinemaByName(String name)
    {
        String query = "select * from cinema where cinema_name = '" + name + "';";
        return dbMan.ExecuteReader(query);
    }

    public ResultSet SelectAdvByName(String name)
    {
        String query = "select * from advertisement where ad_title  = '" + name + "';";
        return dbMan.ExecuteReader(query);
    }

    public ResultSet SelectCrewMemberByName(String name)
    {
        String query = "select * from crew_members where member_name = '" + name + "';";
        return dbMan.ExecuteReader(query);
    }

    public ResultSet SelectAuthorByName(String name)
    {
        String query = "select * from crew_members,authors where member_name = '" + name + "' and authors_id=member_id ;";
        return dbMan.ExecuteReader(query);

    }

    public ResultSet SelectDirectorByName(String name)
    {
        String query = "select * from crew_members,director where member_name = '" + name + "' and director_id=member_id ;";
        return dbMan.ExecuteReader(query);

    }

    public int UpdateActor(String newName,String newDate,String newBrief , String IdName)
    {
        String query = "Update crew_members SET member_name  = '" + newName + "'" +
                " , birth_date  = '" + newDate +"' , brief  = '" + newBrief +"' "+
                "where member_name ="+"'" +IdName+"';";
        return dbMan.ExecuteNonQuery(query);
    }

    public int UpdateAuthor(String newName,String newDate,String newBrief ,String newStyle, String IdName)throws SQLException
    {
        String query = "Update crew_members SET member_name  = '" + newName + "'" +
                " , birth_date  = '" + newDate +"' , brief  = '" + newBrief +"' "+
                "where member_name ="+"'" +IdName+"';";
        int res = dbMan.ExecuteNonQuery(query);
        if (res==1) {
            query = "select * from crew_members where member_name = '" + newName + "' ;";
            ResultSet crew_memb = dbMan.ExecuteReader(query);
            int memberID;
            if (crew_memb.next()) {
                memberID = crew_memb.getInt("member_id");
                query = "Update authors SET writing_style  = '" + newStyle + "'" +
                        "where authors_id ="+"'" +memberID+"';";
                return dbMan.ExecuteNonQuery(query);
            }
        }
        return -1;
    }

    public int UpdateDirector(String newName,String newDate,String newBrief ,String newStyle, String IdName)throws SQLException
    {
        String query = "Update crew_members SET member_name  = '" + newName + "'" +
                " , birth_date  = '" + newDate +"' , brief  = '" + newBrief +"' "+
                "where member_name ="+"'" +IdName+"';";
        int res = dbMan.ExecuteNonQuery(query);
        if (res==1) {
            query = "select * from crew_members where member_name = '" + newName + "' ;";
            ResultSet crew_memb = dbMan.ExecuteReader(query);
            int memberID;
            if (crew_memb.next()) {
                memberID = crew_memb.getInt("member_id");
                query = "Update Director SET making_style  = '" + newStyle + "'" +
                        "where director_id ="+"'" +memberID+"';";
                return dbMan.ExecuteNonQuery(query);
            }
        }
        return -1;
    }

    public int UpdateCinema(String name,String location ,String IdName)throws SQLException
    {
        String query = "Update cinema SET cinema_name   = '" + name + "'" +
                " , cinema_location   = '" + location +"'"+
                "where cinema_name ="+"'" +IdName+"';";
        return dbMan.ExecuteNonQuery(query);
    }

    public int UpdateAdv(String title,String companyname,String startdate,String enddate,String content,String price,String url,String AdvIdentify)throws SQLException
    {
        String query = "Update advertisement SET ad_title = '" + title + "'" +
                " , company_name= '" + companyname +"'"+
                " , ad_start_date= '" + startdate +"'"+
                " , ad_end_date= '" + enddate +"'"+
                " , ad_content= '" + content +"'"+
                " , price = " + price +
                " , url = '" + url +"'"+
                "where ad_title ="+"'" +AdvIdentify+"';";
        return dbMan.ExecuteNonQuery(query);
    }

    public ResultSet SelectAllCinemas()throws SQLException
    {
        String query = "select * from cinema;" ;
        return   dbMan.ExecuteReader(query);

    }

    public int DeleteMovie(String name)throws SQLException
    {
        String query = "delete from movies where movie_name ='"+name+"';" ; //Zero for Director tpyes
        return   dbMan.ExecuteNonQuery(query);
    }

    public int DeleteCinema(String name)throws SQLException
    {
        String query = "delete from cinema where cinema_name ='"+name+"';" ; //Zero for Director tpyes
        return   dbMan.ExecuteNonQuery(query);
    }

    public int DeleteCrewMember(String name)throws SQLException
    {
        String query = "delete from crew_members where member_name ='"+name+"';" ; //Zero for Director tpyes
        return   dbMan.ExecuteNonQuery(query);
    }

    public int DeleteAdv(String name)throws SQLException
    {
        String query = "delete from advertisement where ad_title ='"+name+"';" ; //Zero for Director tpyes
        return   dbMan.ExecuteNonQuery(query);
    }
    //------------------------------------>
    public int InsertRequest(String Name,int ID) throws SQLException
    {
        String checkquery = "select * from  movies where movie_name = '" + Name + "';";
        ResultSet movie_exists = dbMan.ExecuteReader(checkquery);
        if (movie_exists.next())
            return 404; // returns 404 if this crew_member is already exist with same name and birthday

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String query = "Insert into request (req_movie_name,req_date,follower_id)"+
                " values('"+Name+"',"+
                "'"+date+"',"
                +ID+");" ;
        return dbMan.ExecuteNonQuery(query);
    }

    public ResultSet ViewAllRequests() throws SQLException {
        String query = "select req_movie_name from  request ;";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet ViewHistory(int id) throws SQLException {
        String query = "select movie_name from  history h,movies m where h.movie_id=m.movie_id and follower_id="+id+" ;";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet ViewWishlist(int id) throws SQLException {
        String query = "select movie_name from  wishlist w,movies m where w.movie_id=m.movie_id and follower_id="+id+" ;";
        return dbMan.ExecuteReader(query);
    }
    public int ChangePass(String oldpass,String newpass,int id,int priv) throws SQLException {

        if(priv==2) {
            String q="Select fpassword from follower where follower_id="+id+";";
            ResultSet  a = dbMan.ExecuteReader(q);
            a.next();
            String p=a.getString("fpassword");
            if(!p.equals(oldpass))
            {
                return 77;
            }
            String query = "Update follower " +
                    " set fpassword='" + newpass + "'" +
                    " where follower_id=" + id +
                    " And fpassword='" + oldpass + "';";
            return dbMan.ExecuteNonQuery(query);
        }
        else {
            String q="Select a_password from admins where admin_id="+id+";";
            ResultSet  a = dbMan.ExecuteReader(q);
            a.next();
            String p=a.getString("a_password");
            if(!p.equals(oldpass))
            {
                return 77;
            }
            String query = "Update admins " +
                    " set a_password='" + newpass + "'" +
                    " where admin_id=" + id +
                    " And a_password='" + oldpass + "';";
            return dbMan.ExecuteNonQuery(query);
        }
    }
    public int InsertActorToMovie(String ActorName, String moviename,String ChName) throws SQLException {

        String query = "select * from crew_members where member_name = '"+ActorName+"' and member_type=0;";
        ResultSet actor = dbMan.ExecuteReader(query);
        int actorID;
        if(actor.next())
        { actorID=actor.getInt("member_id"); }
        else return 400;// returns 400 if there is no actor with this name

        String query2 = "select * from movies where movie_name = '"+moviename+"';";
        ResultSet movie= dbMan.ExecuteReader(query2);
        int movieID;
        if(movie.next())
        { movieID=movie.getInt("movie_id"); }
        else return 404;// returns 404 if there is no movie with this name

        String checkQuery="select* from actors_movie where actors_id="+actorID+" and movie_id="+movieID+";";
        ResultSet rs = dbMan.ExecuteReader(checkQuery);
        if(!rs.equals(null) && rs.next())
            return 22;

        query="Insert into actors_movie values ("+actorID+","+movieID+",'"+ChName+"');";
        return dbMan.ExecuteNonQuery(query);

    }
    public ResultSet GetActorNames() throws SQLException {

        String query = "select member_name from crew_members where member_type=0;";
        return dbMan.ExecuteReader(query);

    }
    public ResultSet GetMovieNames() throws SQLException {

        String query = "select movie_name from movies ;";
        return dbMan.ExecuteReader(query);

    }
    public int InsertInWishList(int followerId,int movieId)
    {
        String query ="Insert into wishlist values("+followerId+","+movieId+");";
        return dbMan.ExecuteNonQuery(query);

    }
    public int InsertInHistoryList(int followerId,int movieId)
    {
        String query ="Insert into history values("+followerId+","+movieId+");";
        return dbMan.ExecuteNonQuery(query);
    }
    public ResultSet getMostWished()
    {
        String query = "\n" +
                "select movie_name,m.movie_id,count(follower_id) as temp from wishlist w join movies m on m.movie_id =w.movie_id \n" +
                "group by m.movie_id,movie_name \n" +
                "order by temp desc;\n";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getMostViewed()
    {
        String query = "\n" +
                "select movie_name,m.movie_id,count(follower_id) as temp from history w join movies m on m.movie_id =w.movie_id \n" +
                "group by m.movie_id,movie_name \n" +
                "order by temp desc;\n";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getMostRated()
    {
        String query = "select movie_name from movies order by reviewers_rating desc ;";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getNewReales()
    {
        String query = "select movie_name from movies order by release_date desc ;";
        return dbMan.ExecuteReader(query);
    }
    public int recordUserRate(int followerId,int movieId,String rate) throws SQLException {
        String check="select * from follower_rating where movie_id="+movieId+" and follower_id="+followerId+";";
        ResultSet s=dbMan.ExecuteReader(check);
        if(s!=null&&s.next())
        {
            String query="update follower_rating set rating="+rate+" where movie_id="+movieId+";";
            return dbMan.ExecuteNonQuery(query);
        }
        String query ="Insert into follower_rating values("+movieId+","+followerId+","+rate+");";
        return dbMan.ExecuteNonQuery(query);
    }
    public ResultSet getAvgUserRating(int movieId)
    {
        String query="Select Avg(rating) ar from follower_rating where movie_id="+movieId+";";
        return dbMan.ExecuteReader(query);
    }

    ResultSet authors_names ()
    {
        String query = "select member_name,member_id from authors join crew_members on authors_id = member_id ;";
        return  dbMan.ExecuteReader(query);
    }

    ResultSet directors_names ()
    {
        String query = "select member_name,member_id from director join crew_members on director_id = member_id ;";
        return  dbMan.ExecuteReader(query);
    }

    int Add_Genre(String m_id,String Gen)throws SQLException
    {
        String query = "insert into genre values ('"+m_id+"',lower('"+Gen+"'));";
        return dbMan.ExecuteNonQuery(query);
    }

    public int InsertNewMovie(String name,String desc,String lang,
                              String age_rest,String rdate,
                              String reviewers,String url,
                              String duration,int Admin_id,String Aut_id
            ,String dir_id )throws SQLException
    {
        String checkquery ="select * from movies where movie_name = lower('"+name+"') and release_date="+"'"+ rdate+"' ;";
        ResultSet movie_exists = dbMan.ExecuteReader(checkquery);
        if(movie_exists.next())
            return 404; // returns 404 if this crew_member is already exist with same name and birthday
        String query ="insert into movies values (lower('"+name+"'),lower('"+desc+"'),lower('"+lang+"'),"
                +Integer.parseInt(age_rest)+",'"+rdate+"',"+0.0+","+Float.parseFloat(reviewers)+",'"+url+"','"+duration+"',"+Admin_id
                +","+Integer.parseInt(Aut_id)+","+Integer.parseInt(dir_id)+");";

        return dbMan.ExecuteNonQuery(query);
    }
    public int getMovieId(String name,String date)throws SQLException
    {
        String query ="select movie_id from movies where movie_name = '"+name+"' and release_date="+"'"+ date+"' ;";
        ResultSet r = dbMan.ExecuteReader(query);
        if(r.next()) {
            String s =r.getString(1);
            return Integer.parseInt(s);
        }
        return -1;
    }

    public int InsertAward(String Aname,int Atype,String Fname,String Fdate,String Floc)throws SQLException
    {
        String checkquery = "select * from award where award_name = lower('"+Aname+"') and award_type = "+
                Atype + " and fest_name = lower('"+Fname+"') and fest_date = lower('"+Fdate+"') and fest_location = lower('"+
                Floc +"');";
        ResultSet r = dbMan.ExecuteReader(checkquery);
        if(r.next())
            return 404;
        String query = "insert into award values(lower('"+Aname+"'),"+Atype+",lower('"+Fname+"'),lower('"+
                Fdate+"'),lower('"+Floc+"'));";
        return dbMan.ExecuteNonQuery(query);
    }

    public ArrayList<String> SetNamesToArray (ResultSet names , ArrayList<String> id_list) throws SQLException
    {
        ArrayList<String> Names = new ArrayList<String>();

        try{
            while (names.next())
            {
                Names.add(names.getString(1));
                id_list.add(names.getString(2));
            }
        }catch (java.sql.SQLException e)
        {

        }
        return Names;
    }


    public ResultSet get_movies()throws SQLException{

        String query="select movie_name,movie_id from movies; ";
        return dbMan.ExecuteReader(query);
    }

    public ResultSet getAward(int typ)throws SQLException
    {
        String query = "select award_name , award_id from award where award_type = "+typ+";";
        return dbMan.ExecuteReader(query);
    }


//////////////////add_movie///////////////////

    public int UpdateAward(String newName,String festloc,int awardtype,String  festname, String festdate, String oldName)
    {
        String query = "Update award SET award_name  = '" + newName + "'" +
                " , fest_location  = '" + festloc +"' , award_type  = '" + awardtype +"' "+
                " , fest_name  = '" + festname +"' , fest_date  = '" + festdate +"' "+
                " where award_name ="+"'" +oldName+"';";
        return dbMan.ExecuteNonQuery(query);
    }

    public int UpdateMovie(String newName,String language ,String des, String ageR,String url,String reviewerRating,String duration ,String oldName)
    {
        String query = "Update movies SET movie_name  = '" + newName + "'" +
                " , movie_language  = '" + language +"' , movie_description  = '" + des +"' "+
                " , age_resriction = '" + ageR +"' , url  = '" + url +"' "+
                " , reviewers_rating  = '" + reviewerRating +"' , duration  = '" + duration +"' "+
                "where movie_name ="+"'" +oldName+"';";
        return dbMan.ExecuteNonQuery(query);
    }
    public ResultSet SelectAwardByName(String name)
    {
        String query = "select * from award where award_name = '" + name + "';";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet SelectAllAwards() throws SQLException {
        String query = "select * from award;"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);

    }
    public  ResultSet getNoOfMovieForDirector(int directorId)
    {
        String query = "select count(*) from crew_members a,movies m where member_type=2 and a.member_id=m.director_id and director_id="+directorId+";'"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);
    }
    public  ResultSet getNoOfMovieForAuthor(int authorId)
    {
        String query = "select count(*) from crew_members a,movies m where member_type=1 and a.member_id=m.authors_id and authors_id="+authorId+";'"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);
    }
    public  ResultSet getNoOfMovieForActor(int actorId)
    {
        String query = "select count(*) from actors_movie where actors_id="+actorId+";"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);
    }

    public  ResultSet getNoOfAwardsForMember(int memberId)
    {
        String query = "select count (member_id) from movie_member_award where member_id="+memberId+";"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);
    }
    public  ResultSet getNoOfAwardsForMovie(int movieId)
    {
        String query = "select count (movie_id) from movie_member_award where movie_id="+movieId+";'"; //Zero for Director tpyes
        return dbMan.ExecuteReader(query);
    }
    public int SelectMovieIDByName(String name)throws SQLException
    {
        String query = "select * from movies where movie_name = '" + name + "';";
        ResultSet r = dbMan.ExecuteReader(query);
        r.next();
        int id = r.getInt("movie_id");
        return id;
    }
    public int SelectRequestIDByName(String name)throws SQLException
    {
        String query = "select * from request where req_movie_name = '" + name + "';";
        ResultSet r = dbMan.ExecuteReader(query);
        r.next();
        int id = r.getInt("req_id");
        return id;
    }
    public int DeleteFromWsihlist(int uid,int mid)throws SQLException
    {
        String query = "delete from wishlist where follower_id ="+uid+" and movie_id = "+mid+";" ;
        return   dbMan.ExecuteNonQuery(query);
    }

    public int DeleteFromHistory(int uid,int mid)throws SQLException
    {
        String query = "delete from history where follower_id ="+uid+" and movie_id = "+mid+";" ;
        return   dbMan.ExecuteNonQuery(query);
    }
    public int DeleteFromRequest(int rid)throws SQLException
    {
        String query = "delete from request where req_id ="+rid+" ;" ;
        return   dbMan.ExecuteNonQuery(query);
    }
    public ResultSet getEmailAddressAdmins(String username)
    {
        String query="Select email from admins where username='"+username+"';";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getEmailAddressFollowers(String username)
    {
        String query="Select email from follower where username="+username+";";
        return dbMan.ExecuteReader(query);
    }

    //Statistics
    public ResultSet getStatFavCat(int id)throws SQLException{
        String query ="select g.genre, Count(*) from wishlist w join genre g on g.movie_id = w.movie_id where w.follower_id =" +id+" group by g.genre;";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getStatHistCat(int id)throws SQLException{
        String query ="select g.genre, Count(*) from history w join genre g on g.movie_id = w.movie_id where w.follower_id = "+id+" group by g.genre";
        return dbMan.ExecuteReader(query);
    }
    public String getVisitedM(int id)throws SQLException
    {
        String query = "select COUNT(*) from history where follower_id ="+id+";";
        return (String)dbMan.ExecuteScalar(query);
    }
    public String getLikeddM(int id)throws SQLException
    {
        String query = "select COUNT(*) from wishlist where follower_id ="+id+";";
        return (String)dbMan.ExecuteScalar(query);
    }
    public String getRateddM(int id)throws SQLException
    {
        String query = "select COUNT(*) from follower_rating where follower_id ="+id+";";
        return (String)dbMan.ExecuteScalar(query);
    }
    public String getReqdM(int id)throws SQLException
    {
        String query = "select COUNT(*) from request where follower_id ="+id+";";
        return (String)dbMan.ExecuteScalar(query);
    }
    public String getAdminActivityInfo (int t ,int id)throws SQLException {
        String query;
        switch (t) {
            case 0:  //admin movies
                query = "select count(*) from movies m join admins a on m.admin_id=a.admin_id where a.admin_id = " + id + ";";
                return (String) dbMan.ExecuteScalar(query);
            case 1: //admin ads
                query = "select count(*) from advertisement m join admins a on m.admin_id=a.admin_id where a.admin_id ="+id+";";
                return (String)dbMan.ExecuteScalar(query);
            case 2: //admin requests
                query = "select count(*) from request m join admins a on m.admin_id=a.admin_id where a.admin_id ="+id+";";
                return (String)dbMan.ExecuteScalar(query);
        }
        return "0";
    }
    public String getDatabaseInfo(int t)throws SQLException
    {
        String query;
        switch (t)
        {
            case 0 :
                query="Select count(*) from movies ;";
                return (String)dbMan.ExecuteScalar(query);

            case 1 :
                query="Select count(*) from actors ;";
                return (String)dbMan.ExecuteScalar(query);
            case 2 :
                query="Select count(*) from authors ;";
                return (String)dbMan.ExecuteScalar(query);
            case 3 :
                query="Select count(*) from director ;";
                return (String)dbMan.ExecuteScalar(query);
            case 4 :
                query="Select count(*) from cinema ;";
                return (String)dbMan.ExecuteScalar(query);
            case 5 :
                query="Select count(*) from award ;";
                return (String)dbMan.ExecuteScalar(query);
            case 6 :
                query="Select count(*) from advertisement ;";
                return (String)dbMan.ExecuteScalar(query);
            case 7 :
                query="Select count(*) from follower ;";
                return (String)dbMan.ExecuteScalar(query);
            case 8 :
                query="Select count(*) from admins ;";
                return (String)dbMan.ExecuteScalar(query);
        }
        return "0";
    }
    public ResultSet getStatMostLiked()
    {
        String query = "select movie_name,count(follower_id) as temp from wishlist w join movies m on m.movie_id =w.movie_id \n" +
                "                group by m.movie_id,movie_name \n" +
                "                order by temp desc;";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getStatMostViewed()
    {
        String query = "\n" +
                "select movie_name,count(follower_id) as temp from history w join movies m on m.movie_id =w.movie_id \n" +
                "group by m.movie_id,movie_name \n" +
                "order by temp desc;\n";
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getStatMostRated()
    {
        String query = "select movie_name,users_rating from movies order by reviewers_rating desc ;";
        return dbMan.ExecuteReader(query);
    }
    ///Award

    public int add_award_to_movie(String M_id,String A_id,String date)throws SQLException{
        String checkQuery = "select * from movie_member_award where movie_id = "+M_id+"and award_id = "+A_id+"and winning_date = '"+date+"';";
        ResultSet r =dbMan.ExecuteReader(checkQuery);
        if(r.next())
            return 404;
        String query = "insert into movie_member_award (movie_id,award_id,winning_date) values ("+M_id+","+A_id+",'"+date+"');";
        return dbMan.ExecuteNonQuery(query);
    }

    public int add_award_to_author(String M_id,String Act_id ,String A_id,String date)throws SQLException{
        String checkQuery = "select * from movie_member_award where movie_id = "+M_id+"and award_id = "+A_id+"and member_id = "+Act_id+";";
        ResultSet r =dbMan.ExecuteReader(checkQuery);
        if(r.next())
            return 404;

        String checkauthor = "select * from movies where authors_id ="+Act_id+";";
        ResultSet r2 = dbMan.ExecuteReader(checkauthor);
        if(!r2.next())
            return -1;
        String query = "insert into movie_member_award  values ("+M_id+","+Act_id+","+A_id+",'"+date+"');";
        return dbMan.ExecuteNonQuery(query);
    }

    public int add_award_to_director(String M_id,String Act_id ,String A_id,String date)throws SQLException{
        String checkQuery = "select * from movie_member_award where movie_id = "+M_id+"and award_id = "+A_id+"and member_id = "+Act_id+";";
        ResultSet r =dbMan.ExecuteReader(checkQuery);
        if(r.next())
            return 404;

        String checkauthor = "select * from movies where director_id ="+Act_id+";";
        ResultSet r2 = dbMan.ExecuteReader(checkauthor);
        if(!r2.next())
            return -1;
        String query = "insert into movie_member_award  values ("+M_id+","+Act_id+","+A_id+",'"+date+"');";
        return dbMan.ExecuteNonQuery(query);
    }
    public int add_award_to_actor(String M_id,String Act_id ,String A_id,String date)throws SQLException{
        String checkQuery = "select * from movie_member_award where movie_id = "+M_id+"and member_id = "+Act_id+"and award_id = "+A_id+";";
        ResultSet r =dbMan.ExecuteReader(checkQuery);
        if(r.next())
            return 404;
        String checkactor = "select movie_name,m.movie_id from actors_movie a join movies m on m.movie_id = a.movie_id  where a.actors_id ="+Act_id+"; ";
        ResultSet r1 =dbMan.ExecuteReader(checkactor);
        if(!r1.next())
            return -1;
        String query = "insert into movie_member_award  values (" + M_id + "," + Act_id + "," + A_id + ",'" + date + "');";
        return dbMan.ExecuteNonQuery(query);

    }
    public ResultSet actors_names ()throws SQLException
    {
        String query = "select member_name,member_id from actors join crew_members on actors_id = member_id ;";
        return  dbMan.ExecuteReader(query);
    }

    public ResultSet getMemberMovies(int t,String id)throws SQLException{
        String query = "";
        switch (t){
            case (0):  //actor
                query = "select movie_name,m.movie_id from actors_movie a join movies m on m.movie_id = a.movie_id  where a.actors_id ="+id+";";
                break;
            case (1):  //author
                query = "select movie_name,movie_id from movies where authors_id ="+id+";";
                break;
            case (2):  //director
                query = "select movie_name,movie_id from movies where director_id ="+id+";";
                break;
        }
        return dbMan.ExecuteReader(query);
    }
    public ResultSet getAwardName (int id)throws SQLException
    {
        String query = "select award_name from award a,movie_member_award m where a.award_id=m.award_id and member_id="+id+";";
        return  dbMan.ExecuteReader(query);
    }
    public ResultSet cinema_names ()throws SQLException
    {
        String query = "select * from cinema;";
        return  dbMan.ExecuteReader(query);
    }
    public ResultSet movie_cinema_names (String name)throws SQLException
    {
        int id=-1;
        String perquisite="Select cinema_id from cinema where cinema_name='"+name+"';";
        ResultSet temp=dbMan.ExecuteReader(perquisite);
        if(temp!=null&&temp.next())
            id=temp.getInt(1);
        String query = "select movie_name from movies m,available_at a where m.movie_id=a.movie_id and cinema_id="+id+";";
        return  dbMan.ExecuteReader(query);
    }

}


