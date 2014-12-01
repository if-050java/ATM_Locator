$(document).ready(function(){


    //Email validator
    $(document).on('blur', '[name="inputEmail"]', function(){
        var $this = $(this);
        if (validateEmail($this.val())){
            $.ajax({
                url: "/usercredemail?email="+$this.val(),
                async: false,
                type: 'GET',
                dataType: 'json',
                success : function(data)
                {

                    if(data.canUse){
                        $this.tooltip('destroy');
                    }
                    else{
                        $this.tooltip('destroy');
                        $this.attr('title', data.cause);
                        $this.tooltip({placement : 'right', trigger: 'manual'});
                        $this.on({
                            blur: function() {
                                $(this).tooltip('show');
                            }
                        });
                        $('.email-wrapper .tooltip.right .tooltip-inner').css('background', 'red').addClass('error');
                        $('.email-wrapper .tooltip.right .tooltip-arrow').css('border-right-color', 'red');
                    }
                }
            })
        }
        else{
            $this.tooltip('destroy');
            $this.attr('title', 'Your email is not valid');
            $this.tooltip({placement : 'right', trigger: 'manual'});
            $this.on({
                blur: function() {
                    $(this).tooltip('show');
                }
            });
            $('.email-wrapper .tooltip.right .tooltip-inner').css('background', 'red').addClass('error');
            $('.email-wrapper .tooltip.right .tooltip-arrow').css('border-right-color', 'red');
        }
    });


});