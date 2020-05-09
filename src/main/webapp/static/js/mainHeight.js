 $(function() {
     function mainHeight() {
         var border_width = 12;
         var top_height = 70;//$(self.parent.frames['top-bar-frame']).innerHeight();
         var main_height = window.innerHeight - top_height - border_width;
         $('.container').height(main_height);
         $('.tab-content').height(main_height - 30);
     }
     mainHeight();
     $(window).resize(function() {
         mainHeight();
     })
   
 })
