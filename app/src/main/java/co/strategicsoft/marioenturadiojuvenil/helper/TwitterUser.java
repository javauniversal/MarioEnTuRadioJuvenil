package co.strategicsoft.marioenturadiojuvenil.helper;

/**
 * Created by blackxcorpio on 13/12/2014.
 */
import com.google.gson.annotations.SerializedName;


public class TwitterUser {

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_image_url")
    private String profileImageUrl;

    public String getId() {
        return id;
    }

    public String getIdStr() {
        return idStr;
    }

    @SerializedName("id_str")
    private String idStr;

    @SerializedName("id")
    private String id;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
