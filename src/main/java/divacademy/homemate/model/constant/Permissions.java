package divacademy.homemate.model.constant;

public class Permissions {
    /** -------------------User controller------------------------*/
    public static String POST_USER = "post::user";
    public static String GET_CONFIRMATION_USER = "get::user-confirmation";
    public static String POST_USER_DETAILS = "post::user-details";
    public static String GET_ALL_USER = "get::user::all";
    public static String GET_USER_BY_ID = "get::user::id";
    public static String DELETE_USER_BY_ID = "delete::user::id";
    public static String GET_FORGOT_PASSWORD = "get::user-forgot-pass::email-or-phone";
    public static String POST_RESET_PASSWORD_CONFIRM = "post::user-reset-pass-confirm";
    public static String POST_CHANGE_PASSWORD = "post::user-change-pass";
    public static String POST_SUBSCRIPTION = "post::user::subscription";
    public static String POST_SUBSCRIPTION_CONFIRM = "post::user::confirm-subscription";


    /** -------------------Admin controller------------------------*/
    public static String POST_ADMIN = "post::admin";
    public static String POST_ROLE = "post::role";
    public static String PUT_ROLE = "put::role";
    public static String POST_SUBSCRIPTION_TYPE = "post::subs-type";
    public static String GET_SUBSCRIPTION_TYPE = "get::subs-type";
    public static String POST_GENDER_TYPE = "post::gender-type";
    public static String GET_GENDER_TYPE = "get::gender-type";
    public static String PATCH_CHANGE_USER_ROLE = "patch::user-role::id";

    /** -------------------Advert controller------------------------*/

    public static String POST_ADVERT = "post::advert";
    public static String GET_ALL_ADVERT = "get::advert::all";
    public static String GET_ADVERT = "get::advert::id";
    public static String GET_CONNECTION = "get::connection::advert-id";
    public static String POST_PROPERTY_TYPE = "post::property-type";
    public static String GET_ALL_PROPERTY_TYPE = "get::property-type::all";
    public static String POST_BUILDING_TYPE = "post::building-type";
    public static String GET_ALL_BUILDING_TYPE = "get::building-type::all";
    public static String POST_ADVERT_TYPE = "post::advert-type";
    public static String GET_ALL_ADVERT_TYPE = "get::advert-type::all";
    public static String DELETE_ADVERT_BY_ID = "delete::advert::id";

    /** -------------------Auth controller------------------------*/
    public static String POST_LOGIN = "post::login";
    public static String GET_TOKEN_BY_REFRESH = "get::token::refresh";
    public static String GET_OAUTH_LOGIN = "get::auth-login";

}
