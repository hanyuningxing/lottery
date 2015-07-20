<@override name="compoundSchemeContent">
  <#if periodData??&&periodData.result??&&compoundContentDetail??>
      ${compoundContentDetail!}
     <#else>
      ${(scheme.compoundContentText)!}
  </#if>
</@override>
<@extends name="/WEB-INF/content/common/simple-scheme-show.ftl"/>
<@extends name="base.ftl"/>