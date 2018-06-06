<%@ page import="jandcode.web.*; jandcode.dbm.*; jandcode.dbm.data.*; jandcode.wax.core.utils.gf.*" %>
%{--

Редактор для записи

========================================================================= --}%


<jc:include url="js/frame/edit_rec.gf"/>
<gf:attr name="daoname" value="History/updater"/>

%{-- ========================================================================= --}%
<g:javascript>
  th.width = 500;
  //
  var ims = [];
  ims.push(b.datalabel2("dte"))

  if (th.mode == "ins") {
    ims.push(b.input2("fn"))
  } else {
    ims.push(b.input2("description"))
  }

  ims.push(b.input2("cmt", {flex: 1}))

  //
  th.items = ims;


</g:javascript>
