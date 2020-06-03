<#-- @ftlvariable name="self" type="com.coremedia.blueprint.siteimprove.SiteImproveWidget" -->

<#if preview.isPreviewCae()>
<script async src="https://cdn.siteimprove.net/cms/overlay.js"></script>
<script>
  var url =  window.location.href;
  <#--
  not cutting params means that the editor always has to request a re-check but otherwise you cannot preview
  time travel, personalization and fragment previews
   -->
  if(url.indexOf('?') !== -1) {
    url = url.substring(0, url.indexOf('?'));
  }

  <#--
  DEV Sugar?
   -->
  if(url.indexOf('http://localhost:40980/blueprint/servlet') !== -1) {
    url = '${self.cmsToken}' + url.substr('http://localhost:40980/blueprint/servlet/'.length);
  }

  var _si = window._si || [];
  _si.push(['input',  url, '${self.cmsToken}', function() { console.log('Inputted empty string as domain to Siteimprove'); }]);
</script>

<#else>

</#if>
