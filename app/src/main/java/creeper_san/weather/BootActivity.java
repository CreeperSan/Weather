package creeper_san.weather;

import android.support.annotation.Nullable;
import android.os.Bundle;

import org.json.JSONException;

import creeper_san.weather.Base.BaseActivity;
import creeper_san.weather.Exctption.JsonDecodeException;
import creeper_san.weather.Flag.LanguageCode;
import creeper_san.weather.Helper.UrlHelper;
import creeper_san.weather.Item.SearchItem;
import creeper_san.weather.Item.SuggestionItem;

public class BootActivity extends BaseActivity {
    private final String wrong = "asdasdiojaska;d";
    private final String testDat = "{\"HeWeather5\":[{\"basic\":{\"city\":\"西安\",\"cnty\":\"中国\",\"id\":\"CN101060705\",\"lat\":\"42.920415\",\"lon\":\"125.151424\",\"prov\":\"吉林\"},\"status\":\"ok\"},{\"basic\":{\"city\":\"西安\",\"cnty\":\"中国\",\"id\":\"CN101110101\",\"lat\":\"34.263161\",\"lon\":\"108.948024\",\"prov\":\"陕西\"},\"status\":\"ok\"},{\"basic\":{\"city\":\"西安\",\"cnty\":\"中国\",\"id\":\"CN101050311\",\"lat\":\"44.581032\",\"lon\":\"129.61311\",\"prov\":\"黑龙江\"},\"status\":\"ok\"}]}";
    private final String testDatArray = "{\"HeWeather5\":[{\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.547\",\"lon\":\"114.085947\",\"prov\":\"广东\"},\"status\":\"ok\"}]}";
    private final String testEmpty = "{\"HeWeather5\":[{\"status\":\"unknown city\"}]}";
    private final String suggestion = "{\"HeWeather5\":[{\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.547\",\"lon\":\"114.085947\",\"update\":{\"loc\":\"2017-04-29 17:52\",\"utc\":\"2017-04-29 09:52\"}},\"status\":\"ok\",\"suggestion\":{\"air\":{\"brf\":\"中\",\"txt\":\"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。\"},\"comf\":{\"brf\":\"较舒适\",\"txt\":\"白天天气晴好，同时较大的空气湿度，会使您在午后略感闷热，但早晚仍很凉爽、舒适。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"舒适\",\"txt\":\"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，无明显降温过程，发生感冒机率较低。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，较适宜进行各种运动，但因湿度偏高，请适当降低运动强度。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。\"},\"uv\":{\"brf\":\"弱\",\"txt\":\"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。\"}}}]}";
    private final String suggestionArray = "{\"HeWeather5\":[{\"basic\":{\"city\":\"西安\",\"cnty\":\"中国\",\"id\":\"CN101060705\",\"lat\":\"42.920415\",\"lon\":\"125.151424\",\"update\":{\"loc\":\"2017-04-29 17:52\",\"utc\":\"2017-04-29 09:52\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"较舒适\",\"txt\":\"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。\"},\"cw\":{\"brf\":\"较不宜\",\"txt\":\"较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。\"},\"drsg\":{\"brf\":\"舒适\",\"txt\":\"建议着长袖衫裤等服装。体弱者宜着长袖衫裤加马甲。昼夜温差较大，需适时增减衣服，避免感冒着凉。\"},\"flu\":{\"brf\":\"较易发\",\"txt\":\"虽然温度适宜但风力较大，仍较易发生感冒，体质较弱的朋友请注意适当防护。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，但考虑风力较强且气温较低，推荐您进行室内运动，若在户外运动注意防风并适当增减衣物。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，风稍大，但温度适宜，是个好天气哦。适宜旅游，您可以尽情地享受大自然的无限风光。\"},\"uv\":{\"brf\":\"强\",\"txt\":\"紫外线辐射强，建议涂擦SPF20左右、PA++的防晒护肤品。避免在10点至14点暴露于日光下。\"}}},{\"basic\":{\"city\":\"西安\",\"cnty\":\"中国\",\"id\":\"CN101110101\",\"lat\":\"34.263161\",\"lon\":\"108.948024\",\"update\":{\"loc\":\"2017-04-29 17:52\",\"utc\":\"2017-04-29 09:52\"}},\"status\":\"ok\",\"suggestion\":{\"air\":{\"brf\":\"很差\",\"txt\":\"气象条件不利于空气污染物稀释、扩散和清除，请尽量避免在室外长时间活动。\"},\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"热\",\"txt\":\"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，无明显降温过程，发生感冒机率较低。\"},\"sport\":{\"brf\":\"较不宜\",\"txt\":\"天气较好，无雨水困扰，但考虑气温很高，请注意适当减少运动时间并降低运动强度，运动后及时补充水分。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，温度适宜，是个好天气哦。这样的天气适宜旅游，您可以尽情地享受大自然的风光。\"},\"uv\":{\"brf\":\"强\",\"txt\":\"紫外线辐射强，建议涂擦SPF20左右、PA++的防晒护肤品。避免在10点至14点暴露于日光下。\"}}},{\"basic\":{\"city\":\"西安\",\"cnty\":\"中国\",\"id\":\"CN101050311\",\"lat\":\"44.581032\",\"lon\":\"129.61311\",\"update\":{\"loc\":\"2017-04-29 17:52\",\"utc\":\"2017-04-29 09:52\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"舒适\",\"txt\":\"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。\"},\"cw\":{\"brf\":\"不宜\",\"txt\":\"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。\"},\"drsg\":{\"brf\":\"较冷\",\"txt\":\"建议着大衣、呢外套加毛衣、卫衣等服装。体弱者宜着厚外套、厚毛衣。因昼夜温差较大，注意增减衣服。\"},\"flu\":{\"brf\":\"易发\",\"txt\":\"昼夜温差大，风力较强，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。\"},\"sport\":{\"brf\":\"较不宜\",\"txt\":\"有降水，且风力很强，推荐您选择室内运动；若坚持户外运动，请注意保暖并携带雨具。\"},\"trav\":{\"brf\":\"一般\",\"txt\":\"有降水，温度适宜，风大，可能会影响您的出行，旅游指数一般，外出请注意携带雨具并防风。\"},\"uv\":{\"brf\":\"弱\",\"txt\":\"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。\"}}}]}";
    private final String suggestionEmpty = "{\"HeWeather5\":[{\"status\":\"unknown city\"}]}";

    @Override
    protected int getLayout() {return R.layout.activity_boot;}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log(UrlHelper.generateSuggestionUrl("shenzhenzhens", LanguageCode.CHINESE));
//        生活建议
        try {
            SuggestionItem suggestionItem = new SuggestionItem(wrong);
            for (int i=0;i<suggestionItem.getSize();i++){
                log("---------------------------------------");
                log("city "+suggestionItem.getCity(i));
                log("cnty "+suggestionItem.getCountry(i));
                log("id "+suggestionItem.getId(i));
                log("lat "+suggestionItem.getLat(i));
                log("lon "+suggestionItem.getLon(i));
                log("loc "+suggestionItem.getLoc(i));
                log("utc "+suggestionItem.getUtc(i));
                log("comf "+suggestionItem.getComfBrf(i));
                log("comf "+suggestionItem.getComfTxt(i));
                log("cw "+suggestionItem.getCwBrf(i));
                log("cw "+suggestionItem.getCwTxt(i));
                log("drsg "+suggestionItem.getDrsgBrf(i));
                log("drsg "+suggestionItem.getDrsgTxt(i));
                log("flu "+suggestionItem.getFluBrf(i));
                log("flu "+suggestionItem.getFluTxt(i));
                log("sport "+suggestionItem.getSportBrf(i));
                log("sport "+suggestionItem.getSportTxt(i));
                log("trav "+suggestionItem.getTravBrf(i));
                log("trav "+suggestionItem.getTravTxt(i));
                log("uv "+suggestionItem.getUvBrf(i));
                log("uv "+suggestionItem.getUvTxt(i));
            }
        } catch (JSONException | JsonDecodeException e) {
            e.printStackTrace();
        }

//        查询结果
//        try {
//            SearchItem searchItem = new SearchItem(wrong);
//            for (int i=0;i<searchItem.getSize();i++){
//                log("-----------------------");
//                log(searchItem.getID(i));
//                log(searchItem.getCountry(i));
//                log(searchItem.getProvince(i));
//                log(searchItem.getCity(i));
//                log(searchItem.getLat(i));
//                log(searchItem.getLon(i));
//            }
//        } catch (JSONException e) {
//            logw("数据转换出错");
//            e.printStackTrace();
//        } catch (JsonDecodeException e) {
//            logw(e.getType()+" - "+e.getReason()+" - "+e.getErrCode());
//            e.printStackTrace();
//        }
    }

}
