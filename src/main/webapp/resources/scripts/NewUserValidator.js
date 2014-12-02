$(document).ready(function(){


    //Email validator
    $(document).on('blur', '[name="inputEmail"]', function(){
        var $this = $(this);
        if (validateEmail($this.val())){
            $.ajax({
                url: "/usercredemail?email="+$this.val(),
                async: true,
                type: 'GET',
                dataType: 'json',
                success : function(data)
                {
                    if(data.canUse){
                        $this.popover("destroy");
                        $this.removeClass('error');
                        return;
                    }
                    else{
                        $this.attr("data-content", data.cause);
                        $this.popover("show");
                        $this.addClass('error');
                        return;
                    }
                }
            })
        }
        else{
            $this.attr("data-content", "Your email is not valid");
            $this.popover("show");
            $this.addClass('error');
            return;
        }
    });

    $(document).on('blur', '[name="inputLogin"]', function(){
        var $this = $(this);
        if ($this.val().length < 1){
            $this.removeClass('error');
            return;
        }
        if (validateLogin($this.val())){
            $.ajax({
                url: "/usercredlogin?login="+$this.val(),
                async: true,
                type: 'GET',
                dataType: 'json',
                success : function(data)
                {
                    if(data.canUse){
                        $this.popover("destroy");
                        $this.removeClass('error');
                        return;
                    }
                    else{
                        $this.attr("data-content", data.cause);
                        $this.popover("show");
                        $this.addClass('error');
                        return;
                    }
                }
            })
        }
        else{
            $this.attr("data-content", "Your Login is not valid");
            $this.popover("show");
            $this.addClass('error');
            return;
        }
    });

    $(document).on('submit', 'form', function (e) {
        if($('.error').length > 0){
            e.preventDefault();
        }
    });


});