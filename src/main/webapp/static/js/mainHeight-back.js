 $(function() {
     function mainHeight() {
         var border_width = 12;
         var top_height = $(self.parent.frames['top-bar-frame']).innerHeight();
         var main_height = $(self.parent.document).innerHeight() - top_height - border_width;
         $('.container').height(main_height);
         $('.tab-content').height(main_height - 30);
     }
     mainHeight();
     $(window).resize(function() {
         mainHeight();
     })
   
 })
