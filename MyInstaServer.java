
import com.mysql.cj.Messages;
import com.vmm.JHTTPServer;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.text.*;

public class MyInstaServer extends JHTTPServer {

    MyInstaServer(int port) throws Exception {
        super(port);

    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        Response res = new Response(HTTP_OK, "text/plain", "Hello Server ");

        System.out.println(uri + " --->");

        if (uri.contains("GetResource")) {

            uri = uri.substring(1);
            uri = uri.substring(uri.indexOf("/") + 1);
            System.out.println(uri + " -->");
            res = sendCompleteFile(uri);

        } else if (uri.contains("usersignup")) {
            String username = parms.getProperty("username");
            String dispname = parms.getProperty("displayname");
            String password = parms.getProperty("password");
            String email = parms.getProperty("email");
            String phoneno = parms.getProperty("phoneno");
            String gender = parms.getProperty("gender");

            String ans = "";

            try {

                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "'");
                if (rs.next()) {

                    ans = "fail";

                } else {

                    String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
                    String filepath = "src/uploads/" + filename;

                    rs.moveToInsertRow();
                    rs.updateString("username", username);
                    rs.updateString("displayname", dispname);
                    rs.updateString("mobile", phoneno);
                    rs.updateString("gender", gender);
                    rs.updateString("password", password);
                    rs.updateString("email", email);
                    rs.updateString("photo", filepath);
                    rs.insertRow();
                    ans = "success";

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            res = new Response(HTTP_OK, "text/plain", ans);

        } else if (uri.contains("userlogin")) {

            String username = parms.getProperty("username");
            String password = parms.getProperty("password");

            //Database code
            String ans = "";
            try {

                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "' and password='" + password + "'");
                if (rs.next()) {
                    ans = "success";
                } else {
                    ans = "fail";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            res = new Response(HTTP_OK, "text/plain", ans);

        } else if (uri.contains("userchangepass")) {

            String username = parms.getProperty("username");
            String oldpass = parms.getProperty("oldpass");
            String newpass = parms.getProperty("newpass");

            //Database code
            String ans = "";
            try {

                ResultSet rs = DBLoader.executeStatement("select * from users where username='" + username + "' and password='" + oldpass + "'");
                if (rs.next()) {
                    rs.updateString("password", newpass);
                    rs.updateRow();
                    ans = "success";
                } else {
                    ans = "fail";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            res = new Response(HTTP_OK, "text/plain", ans);

        } else if (uri.contains("Search")) {
            String keyword = parms.getProperty("keyword");
            String user_name = parms.getProperty("user_name");
            String ans = "";
            String data = "";
            try {
                ResultSet rs = DBLoader.executeStatement("Select * from users where username like '%" + keyword + "%' ");
                while (rs.next()) {
                    String user_n = rs.getString("username");
                    String photo = rs.getString("photo");

                    ResultSet rs1 = DBLoader.executeStatement("Select * from followtable where followedby = '" + user_name + "' and  followedto = '" + user_n + "' ");
                    if (rs1.next()) {
                        ans = "yes";
                    } else {
                        ans = "no";
                    }
                    data += user_n + ";#" + photo + ";#" + ans + "&";

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("followrequest")) {
            String ans = "";
            String followedto = parms.getProperty("followedto");
            String followedby = parms.getProperty("followedby");
            try {
                ResultSet rs = DBLoader.executeStatement("Select * from followtable where followedby = '" + followedby + "' and followedto = '" + followedto + "'");
                if (rs.next()) {
                    rs.deleteRow();
                    ans = "fail";
                } else {
                    rs.moveToInsertRow();
                    rs.updateString("followedby", followedby);
                    rs.updateString("followedto", followedto);
                    rs.insertRow();
                    ans = "success";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", ans);

        } else if (uri.contains("following")) {
            String data = "";
            String username = parms.getProperty("username");
            try {
                ResultSet rs = DBLoader.executeStatement("Select * from followtable where followedby = '" + username + "' ");
                while (rs.next()) {
                    String followedto = rs.getString("followedto");
                    int fid = rs.getInt("fid");
                    ResultSet rs1 = DBLoader.executeStatement("Select * from users where username = '" + followedto + "'");
                    rs1.next();
                    String photo = rs1.getString("photo");
                    data += followedto + ";#" + fid + ";#" + photo + "$";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("unfollowuser")) {
            //System.out.println("in");
            String fid = parms.getProperty("fid");
            //System.out.println(fid);
            try {

                ResultSet rs = DBLoader.executeStatement("select * from followtable where fid=" + fid + "");
                rs.next();
                rs.deleteRow();

                res = new Response(HTTP_OK, "text/plain", "Unfollowed");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.contains("followers")) {
            String data = "";
            String username = parms.getProperty("username");
            try {
                ResultSet rs = DBLoader.executeStatement("Select * from followtable where followedto = '" + username + "' ");
                while (rs.next()) {
                    String followedby = rs.getString("followedby");
                    int fid = rs.getInt("fid");
                    ResultSet rs1 = DBLoader.executeStatement("Select * from users where username = '" + followedby + "'");
                    rs1.next();
                    String photo = rs1.getString("photo");
                    data += followedby + ";#" + fid + ";#" + photo + "$";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", data);
        } else if (uri.contains("removeuser")) {
            //System.out.println("in");
            String fid = parms.getProperty("fid");
            //System.out.println(fid);
            try {

                ResultSet rs = DBLoader.executeStatement("select * from followtable where fid='" + fid + "'");
                rs.next();
                rs.deleteRow();

                res = new Response(HTTP_OK, "text/plain", "Removed");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.contains("addpost")) {
            String data = "";
            //String ans = "";                
            String caption = parms.getProperty("caption");
            String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
            String filepath = "src/uploads/" + filename;
            String username = parms.getProperty("username");
            Date date = new Date();
            SimpleDateFormat formatOfDate = new SimpleDateFormat("yyyy.MM.dd");
            SimpleDateFormat formatOfTime = new SimpleDateFormat("hh:mm:ss a");
            String post_date = formatOfDate.format(date);
            String post_time = formatOfTime.format(date);

            try {

                ResultSet rs = DBLoader.executeStatement("select * from post_table");

                //rs.next();
                rs.moveToInsertRow();
                rs.updateString("caption", caption);
                rs.updateString("photo", filepath);
                rs.updateString("username", username);
                rs.updateString("date", post_date);
                rs.updateString("time", post_time);
                rs.insertRow();
                //ans  = "success";

                ResultSet rs1 = DBLoader.executeStatement("select MAX(pid) as max from post_table");
                rs1.next();
                int pid = rs1.getInt("max");
                data = Integer.toString(pid);

            } catch (Exception e) {
                e.printStackTrace();
            }
            res = new Response(HTTP_OK, "text/plain", data);

        } else if (uri.contains("addstory")) {
            String caption = parms.getProperty("caption");
            String filename = saveFileOnServerWithRandomName(files, parms, "photo", "src/uploads");
            String filepath = "src/uploads/" + filename;
            String pid = parms.getProperty("pid");

            try {

                ResultSet rs = DBLoader.executeStatement("select * from post_story");

                //rs.next();
                rs.moveToInsertRow();
                rs.updateString("caption", caption);
                rs.updateString("photo", filepath);
                rs.updateString("pid", pid);
                rs.insertRow();

                res = new Response(HTTP_OK, "text/plain", "success");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.contains("showpics")) {
            String pid = parms.getProperty("pid");
            String data = "";

            try {
                ResultSet rs = DBLoader.executeStatement("Select * from post_story where pid = '" + pid + "'");
                while (rs.next()) {
                    String photo = rs.getString("photo");
                    String caption = rs.getString("caption");
                    String psid = rs.getString("psid");

                    data += psid + ";#" + photo + ";#" + caption + "$";
                }
            } catch (Exception ex) {
                Logger.getLogger(MyInstaServer.class.getName()).log(Level.SEVERE, null, ex);
            }

            res = new Response(HTTP_OK, "text/plain", data);
        } else if (uri.contains("closeopr")) {
            String psid = parms.getProperty("psid");
            try {
                ResultSet rs = DBLoader.executeStatement("select * from post_story where psid=" + psid + "");
                rs.next();
                rs.deleteRow();

                res = new Response(HTTP_OK, "text/plain", "Removed");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.contains("wall")) {
            String username = parms.getProperty("username");
            String data = "";
            String ans = "";

            try {
                ResultSet rs = DBLoader.executeStatement("Select * from followtable where followedby = '" + username + "' ");
                while (rs.next()) {
                    String followed_to = rs.getString("followedto");

                    ResultSet rs1 = DBLoader.executeStatement("Select * from post_table where username = '" + followed_to + "' ");
                    while (rs1.next()) {
                        String pid = rs1.getString("pid");
                        String title = rs1.getString("caption");
                        String photo = rs1.getString("photo");
                        String date = rs1.getString("date");

                        ResultSet rs2 = DBLoader.executeStatement("Select * from users where username  = '" + username + "' ");
                        rs2.next();
                        String user_photo = rs2.getString("photo");

                        ResultSet rs3 = DBLoader.executeStatement("Select * from like_table where username = '" + username + "' and pid =" + pid + "");
                        if (rs3.next()) {
                            ans = "yes";
                        } else {
                            ans = "no";
                        }
                        ResultSet rs4 = DBLoader.executeStatement("Select count(*) as count from like_table where pid=" + pid + "");
                        rs4.next();
                        int count = rs4.getInt("count");

                        data += followed_to + "#;" + user_photo + "#;" + title + "#;" + photo + "#;" + date + "#;" + pid + "#;" + ans + "#;" + count + "$";
                    }
                    
                }

                res = new Response(HTTP_OK, "text/plain", data);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.contains("likepost")) {
            String pid = parms.getProperty("pid");
            String username = parms.getProperty("username");
            String ans = "";

            try {

                ResultSet rs = DBLoader.executeStatement("Select * from like_table where username = '" + username + "' and pid = '" + pid + "' ");
                if (rs.next()) {
                    rs.deleteRow();
                    ans = "fail";
                } else {
                    rs.moveToInsertRow();
                    rs.updateString("username", username);
                    rs.updateString("pid", pid);
                    rs.insertRow();
                    ans = "success";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            res = new Response(HTTP_OK, "text/plain", ans);
        }
        
        else if(uri.contains("getstatus"))
        {
            String pid = parms.getProperty("pid");
            String data = "";
            
            try {
                
                ResultSet rs = DBLoader.executeStatement("Select * from post_story where pid = "+pid+" ");
                while(rs.next())
                {
                    String photo = rs.getString("photo");
                    String caption = rs.getString("caption");
                    data += photo + ";#" + caption + "$";
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            res = new Response(HTTP_OK, "text/plain", data);
        }
        
        else if(uri.contains("writecomment"))
        {
            String pid = parms.getProperty("pid");
            String username = parms.getProperty("username");
            String comment = parms.getProperty("comment");
            try {
                ResultSet rs = DBLoader.executeStatement("Select * from comment_table where pid = "+pid+" ");
                rs.moveToInsertRow();
                rs.updateString("comment", comment);
                rs.updateString("username", username);
                rs.updateString("pid", pid);
                rs.insertRow();
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            res = new Response(HTTP_OK, "text/plain", "Comment Added");
            
        }
        
        else if (uri.contains("displaycomments"))
        {
            String pid = parms.getProperty("pid");
            String data = "";
            
            try {

                ResultSet rs = DBLoader.executeStatement("Select * from comment_table where pid="+pid+"");
                while(rs.next())
                {
                    String username = rs.getString("username");
                    String comment = rs.getString("comment");
                    
                    ResultSet rs1 = DBLoader.executeStatement("Select * from users where username = '" +username+ "' ");
                    rs1.next();
                    String user_photo = rs1.getString("photo");
                    
                    data += user_photo + "#;" + username + "#;" + comment + "$";
                }
                
            } catch (Exception e) {
                
                e.printStackTrace();
            }
            
            res = new Response(HTTP_OK, "text/plain", data);
        }

        return res;
    }
}
