package divacademy.homemate.model.constant;

public class EndPoints {
    public static String POST_USER = "api/v1/users";
    public static String GET_CONFIRMATION_USER = "api/v1/users/confirmation";
    public static String POST_USER_DETAILS = "api/v1/users/details";
    public static String GET_ALL_USER = "api/v1/users";
    public static String GET_USER_BY_ID = "api/v1/users/{id}";
    public static String DELETE_USER_BY_ID = "api/v1/users/{id}";
    public static String GET_FORGOT_PASSWORD = "api/v1/users/forgot-password";
    public static String POST_RESET_PASSWORD_CONFIRM = "api/v1/users/reset-password-confirm";
    public static String POST_CHANGE_PASSWORD = "api/v1/users/change-password";
    public static String POST_SUBSCRIPTION = "api/v1/users/subs";
    public static String POST_SUBSCRIPTION_CONFIRM = "api/v1/users/subs-confirm/{subs-id}";


    /**
     * -------------------Admin controller------------------------
     */
    public static String POST_ADMIN = "api/v1/admins";
    public static String POST_ROLE = "api/v1/admins/role";
    public static String PUT_ROLE = "api/v1/admins/role/{id}";
    public static String POST_SUBSCRIPTION_TYPE = "api/v1/admins/subscription-type";
    public static String GET_SUBSCRIPTION_TYPE = "api/v1/admins/subscription-type";
    public static String POST_GENDER_TYPE = "api/v1/admins/genders";
    public static String GET_GENDER_TYPE = "api/v1/admins/genders";
    public static String PATCH_CHANGE_USER_ROLE = "api/v1/admins/change-role";

    /**
     * -------------------Advert controller------------------------
     */

    public static String POST_ADVERT = "api/v1/adverts";
    public static String GET_ALL_ADVERT = "api/v1/adverts";
    public static String GET_ADVERT = "api/v1/adverts/{id}";
    public static String GET_CONNECTION = "api/v1/adverts/connection/{advertId}";
    public static String POST_PROPERTY_TYPE = "api/v1/adverts/property-type";
    public static String GET_ALL_PROPERTY_TYPE = "api/v1/adverts/property-type";
    public static String POST_BUILDING_TYPE = "api/v1/adverts/building-types";
    public static String GET_ALL_BUILDING_TYPE = "api/v1/adverts/building-types";
    public static String POST_ADVERT_TYPE = "api/v1/adverts/advert-type";
    public static String GET_ALL_ADVERT_TYPE = "api/v1/adverts/advert-type";
    public static String DELETE_ADVERT_BY_ID = "api/v1/adverts/{id}";

    /**
     * -------------------Auth controller------------------------
     */
    public static String POST_LOGIN = "api/v1/auth";
    public static String GET_TOKEN_BY_REFRESH = "api/v1/auth";
    public static String GET_OAUTH_LOGIN = "api/v1/auth/oauth-login";

    /**
     * -------------------Swagger controller------------------------
     */

    public static String SWAGGER_V2 = "/v2/api-docs";
    public static String SWAGGER_V3 = "/v3/api-docs";
    public static String SWAGGER_V3_ALL = "/v3/api-docs/**";
    public static String SWAGGER_RESOURCE = "/swagger-resources";
    public static String SWAGGER_RESOURCE_ALL = "/swagger-resources/**";
    public static String SWAGGER_CONFIGURATION_UI = "/configuration/ui";
    public static String SWAGGER_CONFIGURATION_SECURITY = "/configuration/security";
    public static String SWAGGER_UI = "/swagger-ui/**";
    public static String SWAGGER_WEBJARS = "/webjars/**";
    public static String SWAGGER_UI_HTML = "/swagger-ui.html";
}
