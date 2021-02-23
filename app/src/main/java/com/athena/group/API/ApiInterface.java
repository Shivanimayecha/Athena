package com.athena.group.API;


import com.athena.group.Model.AttributeModel;
import com.athena.group.Model.AttributeValueModel;
import com.athena.group.Model.DemoModel;
import com.athena.group.Model.Example;
import com.athena.group.Model.Fstmodel;
import com.athena.group.Model.HSInspectionModel;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.Model.HoursModel;
import com.athena.group.Model.JobReviewModel;
import com.athena.group.Model.LoginResponse;
import com.athena.group.Model.MobilePlantModel;
import com.athena.group.Model.Model;
import com.athena.group.Model.NotificationModel;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.OrderDetailsTruckModel;

import com.athena.group.Model.OrderStatusModel;
import com.athena.group.Model.PastHsModel;
import com.athena.group.Model.PositionModel;
import com.athena.group.Model.ProductModel;
import com.athena.group.Model.ProfileModel;
import com.athena.group.Model.QuestickModel;
import com.athena.group.Model.RecmndedModel;
import com.athena.group.Model.ResultModel;
import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.SiteModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.TimeLogModel;
import com.athena.group.Model.UserDataResponse;
import com.athena.group.Model.VDRLModel;
import com.athena.group.Model.sitelogModel;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("UserData/show_notification")
    public Observable<NotificationModel> show_notification(@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/get_job_reviews_report")
    public Observable<PastHsModel> get_jobReviewRport(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/delate_job_review_report")
    public Observable<PastHsModel> delate_jobReview_report(@Field("user_id") String user_id,
                                                           @Field("jb_id") String jb_id);

    @FormUrlEncoded
    @POST("UserData/get_single_job_reviews_report")
    public Observable<JobReviewModel> get_single_job_reviews_report(@Field("jb_id") String jb_id,
                                                                    @Field("user_id") String user_id);

    @Multipart
    @POST("UserData/add_job_reviews")
    public Observable<Model> insert_jobReview(@Part("jb_user_id") RequestBody jb_user_id,
                                              @Part("jb_site") RequestBody jb_site,
                                              @Part("jb_laber") RequestBody jb_laber,
                                              @Part("jb_plant") RequestBody jb_plant,
                                              @Part("jb_quantity") RequestBody jb_quantity,
                                              @Part("jb_work_on_site_notes") RequestBody jb_work_on_site_notes,
                                              @Part("jb_programs") RequestBody jb_programs,
                                              @Part("jb_day_work_ans") RequestBody jb_day_work_ans,
                                              @Part("jb_day_work_notes") RequestBody jb_day_work_notes,
                                              @Part("jb_issue_site_ans") RequestBody jb_issue_site_ans,
                                              @Part("jb_issue_site_notes") RequestBody jb_issue_site_notes,
                                              @Part("jb_outstanding_ans") RequestBody jb_outstanding_ans,
                                              @Part("jb_outstanding_notes") RequestBody jb_outstanding_notes,
                                              @Part("jb_date") RequestBody jb_date,
                                              @Part List<MultipartBody.Part> files,
                                              @Part MultipartBody.Part file,
                                              // @Part("jb_id") RequestBody jb_id,
                                              @Part("flag") RequestBody flag);

    @Multipart
    @POST("UserData/add_job_reviews")
    public Observable<Model> update_jobReview(@Part("jb_user_id") RequestBody jb_user_id,
                                              @Part("jb_site") RequestBody jb_site,
                                              @Part("jb_laber") RequestBody jb_laber,
                                              @Part("jb_plant") RequestBody jb_plant,
                                              @Part("jb_quantity") RequestBody jb_quantity,
                                              @Part("jb_work_on_site_notes") RequestBody jb_work_on_site_notes,
                                              @Part("jb_programs") RequestBody jb_programs,
                                              @Part("jb_day_work_ans") RequestBody jb_day_work_ans,
                                              @Part("jb_day_work_notes") RequestBody jb_day_work_notes,
                                              @Part("jb_issue_site_ans") RequestBody jb_issue_site_ans,
                                              @Part("jb_issue_site_notes") RequestBody jb_issue_site_notes,
                                              @Part("jb_outstanding_ans") RequestBody jb_outstanding_ans,
                                              @Part("jb_outstanding_notes") RequestBody jb_outstanding_notes,
                                              @Part("jb_date") RequestBody jb_date,
                                              @Part List<MultipartBody.Part> files,
                                              @Part MultipartBody.Part file,
                                              @Part("jb_id") RequestBody jb_id,
                                              @Part("flag") RequestBody flag,
                                              @Part("jb_old_images") RequestBody jb_old_images);


    @GET("UserData/get_fairfix_question_list")
    Observable<SiteContractorModel> get_allPlant();

    @GET("UserData/get_all_mobile_plant_question")
    Observable<QuestickModel> get_mobilePlantQuestion();

    @GET("UserData/recommended_mobile_plant_question")
    Observable<RecmndedModel> recommended_mobilePlantQuestion();

    @FormUrlEncoded
    @POST("UserData/get_mobile_palnt_report")
    public Observable<PastHsModel> get_mblPlantReport(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/insert_mobile_first_data")
    public Observable<Model> insert_mblFirstData(@Field("ans_user_id") String ans_user_id,
                                                 @Field("ans_contact_name") String ans_username,
                                                 @Field("ans_contact_number") String user_comment,
                                                 @Field("ans_plant_type") String ans_project,
                                                 @Field("ans_date_of_exam") String ans_worklocation,
                                                 @Field("ans_palnt_model") String ans_site_id,
                                                 @Field("ans_operator_name") String ans_date_time_off_ins,
                                                 @Field("ans_company_name") String ans_name_contact_manager,
                                                 @Field("ans_w_c") String ans_inspected_by,
                                                 @Field("ans_w_e") String ans_nameofsup,
                                                 @Field("ans_date_of_lastservice") String ans_report_reference,
                                                 @Field("ans_date_of_nextservice") String ans_date_of_nextservice);

    @POST("UserData/insert_mobile_second_step")
    public Observable<Model> insert_mblSecondStep(
            @Body MobilePlantModel mobilePlantModel);

    @Multipart
    @POST("UserData/insert_mobile_last_step")
    public Observable<Model> insert_mblLastStep(@PartMap Map<String, RequestBody> map);

    ///////////////////// hs inspection ////////////////////////////////////

    @FormUrlEncoded
    @POST("UserData/get_hs_question_report")
    public Observable<PastHsModel> get_hsQuestionReport(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("UserData/panding_hs_question_report")
    public Observable<PastHsModel> get_hsQuestionPending(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("UserData/get_hs_inspetion_report")
    public Observable<PastHsModel> get_hsInspetionReport(@Field("user_id") String user_id);

    @POST("UserData/get_all_hsquestion")
    public Observable<HSQuestionModel> get_allQuestion();

    @FormUrlEncoded
    @POST("UserData/detail_hs_question_report")
    public Observable<ResultModel> get_allQuestion_data(@Field("last_step") String user_id);


    @FormUrlEncoded
    @POST("UserData/back_hsquestion_topic_data")
    public Observable<DemoModel> back_hsquestion_topic_data(@Field("last_step") String user_id, @Field("ans_label") String ans_label);


    @POST("UserData/get_all_hsinspetion_list")
    public Observable<HSQuestionModel> get_all_HSQuestion();

    @POST("UserData/get_all_inspetion_status")
    public Observable<SpinnerModel> get_allInspetionStatus();

    @POST("UserData/get_all_hs_question_no_status")
    public Observable<SpinnerModel> get_all_hs_QuestionStatus();

    @POST("UserData/insert_hsquestion_details")
    public Observable<Model> insertHsquestionDetails(
            @Body Example example);

    @POST("UserData/insert_inspection_hs_details")
    public Observable<Model> insert_inspectionHsDetails(
            @Body HSInspectionModel inspectionModel);

    /*@Multipart
    @POST("UserData/insert_hsquestion_imgdata")
    public Observable<Model> insertHSImage  (@PartMap Map<String, RequestBody> map);*/

    @Multipart
    @POST("UserData/insert_hsquestion_imgdata")
    public Observable<Model> insertHSImage(@Part("ans_user_id") RequestBody img_user_id,
                                           @Part("ans_sumofmang") RequestBody img_label,
                                           @Part("last_step") RequestBody last_step,
                                           @Part("ans_completion") RequestBody ans_completion,
                                           @Part MultipartBody.Part image_ins,
                                           @Part MultipartBody.Part image_sprvsr,
                                           @Part List<MultipartBody.Part> files);

    /*@Multipart
    @POST("UserData/insert_inspector_imgdata_last_step")
    public Observable<Model> insert_hsLastStep(@PartMap Map<String, RequestBody> map);*/

    /*@Multipart
    @POST("UserData/insert_inspector_imgdata")
    public Observable<Model> insert_inspector_imgdata(@PartMap Map<String, RequestBody> map);*/

    /* @Multipart
     @POST("UserData/insert_inspector_imgdata")
     public Observable<Model> insert_inspector_imgdata(@Part("img_user_id") int img_user_id,
                                                       @Part("img_label") String img_label,
                                                       @Part("last_step") String last_step,
                                                       @Part List<MultipartBody.Part> files);*/
    @Multipart
    @POST("UserData/insert_inspector_imgdata")
    public Observable<Model> insert_inspector_imgdata(@Part("img_user_id") int img_user_id,
                                                      @Part("img_label") RequestBody img_label,
                                                      @Part("last_step") RequestBody last_step,
                                                      @Part List<MultipartBody.Part> files);

    @Multipart
    @POST("UserData/insert_inspector_imgdata_last_step")
    public Observable<Model> insert_hsLastStep(@Part("ans_user_id") RequestBody img_user_id,
                                               @Part("ans_sumofmang") RequestBody img_label,
                                               @Part("last_step") RequestBody last_step,
                                               @Part("last_ins_name") RequestBody last_ins_name,
                                               @Part("last_ins_date") RequestBody last_ins_date,
                                               @Part("last_site_manager_name") RequestBody last_site_manager_name,
                                               @Part MultipartBody.Part image_ins,
                                               @Part MultipartBody.Part image_sprvsr,
                                               @Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST("UserData/insert_inspection_first_details")
    public Observable<Model> insert_inspctnFormDetail(@Field("ans_user_id") String ans_user_id,
                                                      @Field("ans_username") String ans_username,
                                                      @Field("user_comment") String user_comment,
                                                      @Field("ans_project") String ans_project,
                                                      @Field("ans_worklocation") String ans_worklocation,
                                                      @Field("ans_site_id") String ans_site_id,
                                                      @Field("ans_date_time_off_ins") String ans_date_time_off_ins,
                                                      @Field("ans_name_contact_manager") String ans_name_contact_manager,
                                                      @Field("ans_inspected_by") String ans_inspected_by,
                                                      @Field("ans_nameofsup") String ans_nameofsup,
                                                      @Field("ans_report_reference") String ans_report_reference);

    @FormUrlEncoded
    @POST("UserData/insert_hsquestion_first_details")
    public Observable<Model> insert_question_FirstDetails(@Field("ans_user_id") String ans_user_id,
                                                          @Field("ans_username") String ans_username,
                                                          @Field("user_comment") String user_comment,
                                                          @Field("ans_project") String ans_project,
                                                          @Field("ans_worklocation") String ans_worklocation,
                                                          @Field("ans_site_id") String ans_site_id,
                                                          @Field("ans_date_time_off_ins") String ans_date_time_off_ins,
                                                          @Field("ans_name_contact_manager") String ans_name_contact_manager,
                                                          @Field("ans_inspected_by") String ans_inspected_by,
                                                          @Field("ans_nameofsup") String ans_nameofsup,
                                                          @Field("ans_report_reference") String ans_report_reference,
                                                          @Field("last_step") String last_step);


    //@Multipart
   /* @FormUrlEncoded
    @POST("UserData/insert_hsquestion_details")
    public Observable<Model> insertHsquestionDetails(
            @Field("ans_comment") String ans_comment,
            @Field("ans_needdate") String ans_needdate,
            @Field("ans_user_id") String ans_user_id,
            @Field("last_step") String last_step,
            @Field("questiondata") List<Example.questiondata> questiondata
    );*/

    @FormUrlEncoded
    @POST("UserData/login")
    public Observable<LoginResponse> loginUser(@Field("email") String email,
                                               @Field("password") String password,
                                               @Field("fcm_key") String fcmkey);

    @FormUrlEncoded
    @POST("UserData/forgot_password_api")
    public Observable<LoginResponse> forgotPass(@Field("email") String email);

    @FormUrlEncoded
    @POST("UserData/delate_panding_hs_question_report")
    public Observable<UserDataResponse> delate_panding_hs_question_report(@Field("user_id") String user_id, @Field("last_step") String last_step);

    @FormUrlEncoded
    @POST("UserData/first_hsquestion_topic_data")
    public Observable<Fstmodel> first_hsquestion_topic_data(@Field("last_step") String user_id);

    @FormUrlEncoded
    @POST("UserData/getUserData")
    public Observable<UserDataResponse> getUserDetails(@Field("email") String email);


    @POST("UserData/get_all_supervisor")
    public Observable<SpinnerModel> get_all_supervisor();

    @FormUrlEncoded
    @POST("UserData/get_user_site")
    public Observable<SpinnerModel> get_userSite(@Field("userId") String user_id);

    @POST("UserData/get_site")
    public Observable<SpinnerModel> get_site();

    @FormUrlEncoded
    @POST("UserData/get_truck_driver_site")
    public Observable<SpinnerModel> get_truckDriver_site(@Field("ao_trusr_id") String ao_trusr_id);

    @FormUrlEncoded
    @POST("UserData/get_site_contractor")
    public Observable<SiteContractorModel> get_siteContractor(@Field("Site") String user_id);

    @Multipart
    @POST("UserData/add_work_log")
    public Observable<TimeLogModel> addWorkLog(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("UserData/combine_add_work_log")
    public Observable<TimeLogModel> combine_add_work_log(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST("UserData/edit_work_log")
    public Observable<TimeLogModel> editWorkLog(@PartMap Map<String, RequestBody> map);

    @POST("UserData/getallproducts")
    public Observable<ProductModel> get_allproducts();

    @FormUrlEncoded
    @POST("UserData/get_sub_attribute")
    public Observable<AttributeModel> getsubAttribute(@Field("prd_id") String prd_id,
                                                      @Field("ca_label") String ca_label);

    @FormUrlEncoded
    @POST("UserData/get_sub_attribute_details")
    public Observable<AttributeModel> getAttributevalue(@Field("prd_id") String prd_id,
                                                        @Field("ca_sub_label") String ca_label);

    @GET("UserData/get_position")
    public Observable<PositionModel> getPosition();

    @GET("UserData/get_all_sub_contactor")
    public Observable<SiteContractorModel> get_allSub_contractor();


    @FormUrlEncoded
    @POST("UserData/get_product_attributes")
    public Observable<AttributeModel> getAttribute(@Field("prd_id") String prd_id);


    @FormUrlEncoded
    @POST("UserData/get_attribute_details")
    public Observable<AttributeValueModel> getAttributeValues(@Field("prd_id") String prd_id,
                                                              @Field("ca_label") String ca_label);

    @FormUrlEncoded
    @POST("UserData/update_profile")
    public Observable<UserDataResponse> updatePersonalDetail(@Field("userId") String userId,
                                                             @Field("fname") String fname,
                                                             @Field("lname") String lname,
                                                             @Field("position") String position,
                                                             @Field("mobile") String mobile,
                                                             @Field("address_line") String address_line,
                                                             @Field("password") String password);

    @FormUrlEncoded
    @POST("UserData/place_order")
    public Observable<SpinnerModel> placeOrder(@Field("ord_site_id") String ord_site_id,
                                               @Field("ord_prd_id") String ord_prd_id,
                                               //@Field("quant_m3") String quant_m3,
                                               @Field("mix") String mix,
                                               @Field("aggregate_size") String aggregate_size,
                                               @Field("slump") String slump,
                                               @Field("ord_qty") String ord_qty,
                                               @Field("ord_time") String ord_time,
                                               @Field("ord_pump") String ord_pump,
                                               @Field("ord_notes") String ord_notes,
                                               @Field("ord_delivery_date") String ord_delivery_date,
                                               @Field("ord_user_id") String ord_user_id);


    @FormUrlEncoded
    @POST("UserData/place_order")
    public Observable<SpinnerModel> placeOrder_aggregate(@Field("ord_site_id") String ord_site_id,
                                                         @Field("ord_prd_id") String ord_prd_id,
                                                         @Field("type") String type,
                                                         @Field("ord_qty") String ord_qty,
                                                         @Field("ord_time") String ord_time,
                                                         @Field("ord_notes") String ord_notes,
                                                         @Field("ord_delivery_date") String ord_delivery_date,
                                                         @Field("ord_user_id") String ord_user_id);


    @FormUrlEncoded
    @POST("UserData/place_order")
    public Observable<SpinnerModel> placeOrder_material(@Field("ord_site_id") String ord_site_id,
                                                        @Field("ord_product_label") String ord_product_label,
                                                        @Field("ord_prd_id") String ord_prd_id,
                                                        @Field("ord_label") String ord_label,
                                                        @Field("ord_concrete") String ord_concrete,
                                                        @Field("ord_mesh") String ord_mesh,
                                                        @Field("ord_generalbulding_material") String ord_generalbulding_material,
                                                        @Field("ord_ppe") String ord_ppe,
                                                        @Field("ord_smalltools") String ord_smalltools,
                                                        @Field("ord_plastic_drainage_polypipe") String ord_plastic_drainage_polypipe,
                                                        @Field("ord_polysewer") String ord_polysewer,
                                                        @Field("ord_plastic_drainage_wavin") String ord_plastic_drainage_wavin,
                                                        @Field("ord_clay_drainage") String ord_clay_drainage,
                                                        @Field("ord_kerbs") String ord_kerbs,
                                                        @Field("ord_flags") String ord_flags,
                                                        @Field("ord_blockpaves") String ord_blockpaves,
                                                        @Field("ord_qty") String ord_qty,
                                                        @Field("ord_time") String ord_time,
                                                        @Field("ord_notes") String ord_notes,
                                                        @Field("ord_delivery_date") String ord_delivery_date,
                                                        @Field("ord_user_id") String ord_user_id);


    @Multipart
    @POST("UserData/UploaduserImage")
    Observable<UserDataResponse> upload_profile_image(
            @PartMap Map<String, RequestBody> map);

    //////////////////////  cart list api /////////////////////////////////


    @FormUrlEncoded
    @POST("UserData/add_cart_order")
    public Observable<SpinnerModel> addCart_concrete(@Field("ord_site_id") String ord_site_id,
                                                     @Field("ord_prd_id") String ord_prd_id,
                                                     //@Field("quant_m3") String quant_m3,
                                                     @Field("mix") String mix,
                                                     @Field("aggregate_size") String aggregate_size,
                                                     @Field("slump") String slump,
                                                     @Field("ord_qty") String ord_qty,
                                                     @Field("ord_time") String ord_time,
                                                     @Field("ord_pump") String ord_pump,
                                                     @Field("ord_notes") String ord_notes,
                                                     @Field("ord_delivery_date") String ord_delivery_date,
                                                     @Field("ord_user_id") String ord_user_id);


    @FormUrlEncoded
    @POST("UserData/add_cart_order")
    public Observable<SpinnerModel> addCart_aggregate(@Field("ord_site_id") String ord_site_id,
                                                      @Field("ord_prd_id") String ord_prd_id,
                                                      @Field("type") String type,
                                                      @Field("ord_qty") String ord_qty,
                                                      @Field("ord_time") String ord_time,
                                                      @Field("ord_notes") String ord_notes,
                                                      @Field("ord_delivery_date") String ord_delivery_date,
                                                      @Field("ord_user_id") String ord_user_id);

    @FormUrlEncoded
    @POST("UserData/add_cart_order")
    public Observable<SpinnerModel> addCart_material(@Field("ord_site_id") String ord_site_id,
                                                     @Field("ord_product_label") String ord_product_label,
                                                     @Field("ord_prd_id") String ord_prd_id,
                                                     @Field("ord_label") String ord_label,
                                                     @Field("ord_concrete") String ord_concrete,
                                                     @Field("ord_mesh") String ord_mesh,
                                                     @Field("ord_generalbulding_material") String ord_generalbulding_material,
                                                     @Field("ord_ppe") String ord_ppe,
                                                     @Field("ord_smalltools") String ord_smalltools,
                                                     @Field("ord_plastic_drainage_polypipe") String ord_plastic_drainage_polypipe,
                                                     @Field("ord_polysewer") String ord_polysewer,
                                                     @Field("ord_plastic_drainage_wavin") String ord_plastic_drainage_wavin,
                                                     @Field("ord_clay_drainage") String ord_clay_drainage,
                                                     @Field("ord_kerbs") String ord_kerbs,
                                                     @Field("ord_flags") String ord_flags,
                                                     @Field("ord_blockpaves") String ord_blockpaves,
                                                     @Field("ord_qty") String ord_qty,
                                                     @Field("ord_time") String ord_time,
                                                     @Field("ord_notes") String ord_notes,
                                                     @Field("ord_delivery_date") String ord_delivery_date,
                                                     @Field("ord_user_id") String ord_user_id);

    @FormUrlEncoded
    @POST("UserData/get_user_cartdata")
    public Observable<OrderDetailsModel> get_userCartdata(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/CountCart")
    Observable<SpinnerModel> getCartCont(@Field("user_id") String keyId);


    @FormUrlEncoded
    @POST("UserData/removeCart")
    Observable<SpinnerModel> removeProduct(@Field("cart_id") String cart_id,
                                           @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("UserData/placeorder")
    public Observable<OrderDetailsModel> cart_placeOrder(@Field("cart_id") String cart_id);


    //////////////////////// time log histoty api //////////////////


    @FormUrlEncoded
    @POST("UserData/get_site_log_history")
    public Observable<sitelogModel> get_siteloghistory(@Field("user_id") String user_id,
                                                       @Field("frmdate") String frmdate,
                                                       @Field("tdate") String tdate,
                                                       @Field("site") String site);

    @FormUrlEncoded
    @POST("UserData/get_sub_contactor_log_history")
    public Observable<sitelogModel> get_subcontactorloghistory(@Field("user_id") String user_id,
                                                               @Field("frmdate") String frmdate,
                                                               @Field("tdate") String tdate,
                                                               @Field("contractor_id") String contractor_id);

    @FormUrlEncoded
    @POST("UserData/data_log_history"
    )
    public Observable<sitelogModel> get_dateLogHistory(@Field("user_id") String user_id,
                                                       @Field("frmdate") String frmdate,
                                                       @Field("tdate") String tdate);

    @FormUrlEncoded
    @POST("UserData/get_month_hrs")
    public Observable<HoursModel> get_monthHours(@Field("site") String site);

    @FormUrlEncoded
    @POST("UserData/get_week_hrs")
    public Observable<HoursModel> get_weekHours(@Field("site") String site);

    @FormUrlEncoded
    @POST("UserData/cotractor_get_month_hrs")
    public Observable<HoursModel> get_conMonthHours(@Field("contractor_id") String contractor_id);

    @FormUrlEncoded
    @POST("UserData/cotractor_get_week_hrs")
    public Observable<HoursModel> get_conWeekHours(@Field("contractor_id") String contractor_id);

    ///////////////// order log history //////////////////////////////////

    @GET("UserData/get_all_order_status")
    public Observable<OrderStatusModel> get_allOrderStatus();

    @FormUrlEncoded
    @POST("UserData/get_user_order_history")
    public Observable<OrderDetailsModel> get_allOrderHistory(@Field("ord_site_id") String site);

    @FormUrlEncoded
    @POST("UserData/all_assign_order")
    public Observable<OrderDetailsTruckModel> all_assign_order(@Field("ao_trusr_id") String ao_trusr_id);

    @FormUrlEncoded
    @POST("UserData/get_single_assign_order")
    public Observable<OrderDetailsTruckModel> get_single_assign_order(@Field("assign_ord_id") String assign_ord_id);

    @FormUrlEncoded
    @POST("UserData/get_assign_order")
    public Observable<OrderDetailsTruckModel> get_TruckassignOrder(@Field("ao_trusr_id") String ao_trusr_id,
                                                                   @Field("ao_date") String ao_date);
  /*  @Field("ao_site_id")
    String ao_site_id*/

    @FormUrlEncoded
    @POST("UserData/get_status_wise_assign_order")
    public Observable<OrderDetailsTruckModel> get_truckStatusOrderHistory(@Field("ao_trusr_id") String ao_trusr_id,
                                                                          @Field("ao_ord_status") String ao_ord_status);

//    @Field("ao_site_id") String ao_site_id,

    @FormUrlEncoded
    @POST("UserData/get_open_order")
    public Observable<OrderDetailsModel> get_openOrders(@Field("ord_site_id") String site);

    @FormUrlEncoded
    @POST("UserData/get_single_open_order")
    public Observable<OrderDetailsTruckModel> get_single_open_order(@Field("ord_id") String ord_id);

    @FormUrlEncoded
    @POST("UserData/get_todays_assign_order")
    public Observable<OrderDetailsTruckModel> get_truckTodaysOrder(@Field("ao_trusr_id") String usrid);

    @FormUrlEncoded
    @POST("UserData/get_truck_driver_completed_order")
    public Observable<OrderDetailsTruckModel> get_cmpltdorder(@Field("ao_trusr_id") String usrid);

    /*@Field("ao_site_id")
    String site*/

    @FormUrlEncoded
    @POST("UserData/get_status_wise_order_history")
    public Observable<OrderDetailsModel> get_statusOrderHistory(@Field("ord_site_id") String site,
                                                                @Field("ord_status") String ord_status);

    /*@FormUrlEncoded
    @POST("UserData/confirm_dilivered_order")
    public Observable<TimeLogModel> confirmOrder(@Field("ord_id") String site);*/

    //@FormUrlEncoded
    @Multipart
    @POST("UserData/confirm_dilivered_order")
    public Observable<TimeLogModel> confirmOrder(@Part("coc_user_id") RequestBody coc_user_id,
                                                 @Part("ord_id") RequestBody ord_id,
                                                 @Part("coc_comment") RequestBody img_label,
                                                 @Part List<MultipartBody.Part> files);


    //////////// truck driver panel /////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST("UserData/get_truck_user_vehicals")
    public Observable<SpinnerModel> get_vehicle(@Field("trusr_id") String trusr_id);

    @FormUrlEncoded
    @POST("UserData/get_driver_defect_report")
    public Observable<VDRLModel> get_driverDefectReport(@Field("dd_trusr_id") String trusr_id);

    @Multipart
    @POST("UserData/add_driver_daily_reports")
    public Observable<LoginResponse> add_driverDailyReport(@PartMap Map<String, RequestBody> map);

    @FormUrlEncoded
    @POST("UserData/truck_driver_status_change")
    public Observable<OrderDetailsModel> truck_OrderStatusmanagement(@Field("cao_id") String cao_id,
                                                                     @Field("cao_ord_id") String cao_ord_id,
                                                                     @Field("cao_site_id") String cao_site_id,
                                                                     @Field("cao_trusr_id") String cao_trusr_id,
                                                                     @Field("cao_truck_id") String cao_truck_id,
                                                                     @Field("cao_ord_status") String cao_ord_status,
                                                                     @Field("cao_pickup_location") String cao_pickup_location);

    @Multipart
    @POST("UserData/add_driver_order_details")
    public Observable<OrderDetailsModel> truck_addOrderDetails(@PartMap Map<String, RequestBody> map);


    //////////////////////////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("UserData/getUserData")
    Call<ProfileModel> profile
    (@Field("email") String user_email);


    @FormUrlEncoded
    @POST("UserData/get_user_site")
    Call<SpinnerModel> getJSONString
            (@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/get_position")
    Call<PositionModel> getPosition
            (@Field("userId") String user_id);

    @FormUrlEncoded
    @POST("UserData/update_profile")
    Call<JsonElement> getUpdateProfile
            (@Field("userId") String user_id, @Field("fname") String fname, @Field("position") String position);

    @FormUrlEncoded
    @POST("UserData/UploaduserImage")
    Call<JsonElement> UpdateImage
            (@Part("userId") RequestBody user_id, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("UserData/get_site_contractor")
    Call<SiteModel> getSiteContractor
            (@Field("Site") String site);

    //GetSite Number Site Number Wise
    @FormUrlEncoded
    @POST("UserData/get_contractor_no_wise_site_name")
    public Observable<SpinnerModel> get_site1(@Field("site_number") String site);

    //GetSite sitecode  Site id Wise
    @FormUrlEncoded
    @POST("UserData/get_sitewise_sitecode_data")
    public Observable<SpinnerModel> get_site2(@Field("site_id") String site);
}