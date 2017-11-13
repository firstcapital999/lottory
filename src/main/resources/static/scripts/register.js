/**
 * Created by thinkive on 2017/10/11.
 */

$("#register").click(function(){
    $.post("/regist",$("form").serialize(),function(data){

        if(data.code==0){
            window.location.href = "/login";
        }

    })
})
