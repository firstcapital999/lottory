/**
 * Created by thinkive on 2017/10/11.
 */

$("#register").click(function(){
    $.post("/regist",$("form").serialize(),function(data){
        alert(data);
        debugger;
        if(data.code==0){
            debugger;
            window.location.href = "/login";
        }

    })
})
