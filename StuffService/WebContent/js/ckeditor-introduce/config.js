/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// http://docs.ckeditor.com/#!/api/CKEDITOR.config

	// The toolbar groups arrangement, optimized for two toolbar rows.
	config.toolbarGroups = [
		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing',     groups: [ 'find', 'selection', 'spellchecker' ] },
		{ name: 'links' ,	   groups: [ 'link','unlink'] }, 		 
		{ name: 'insert' },
		{ name: 'forms' },
		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
		'/',
		{ name: 'others' },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
		{ name: 'styles' },
		{ name : 'colors'}
	];

	config.width = 761;

	config.height = 800;

	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	config.removeButtons = 'Subscript,Superscript';

	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;h4;h5;pre';

	config.line_height="0.5em;0.6em;0.7em;0.8em;0.9em;1em;1.1em;1.2em;1.3em;1.4em;1.5em";

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';

	config.extraPlugins = 'image2,toolbar,notification,notificationaggregator,widget,uploadwidget,filetools,lineutils,uploadimage,panel,floatpanel,panelbutton,colorbutton,colordialog,lineheight,richcombo,listblock,font,justify';

	config.uploadUrl = 'http://service.1yingli.cn/yiyingliService/upintroduceckimage'

	config.skin = 'moono';

	config.allowedContent = true;

	config.colorButton_enableMore = true;

	config.font_style = {
    	element: 'p',
    	styles: { 'font-family': '#(family)' },
    	overrides: [{ element: 'font', attributes: { 'face': null } }]
	};

	config.fontSize_sizes = '12/12px;14/14px;16/16px;18/18px;20/20px;24/24px;28/28px;36/36px;48/48px;72/72px';

	config.lineHeight_style = {
		element: 'p',
		styles: { 'line-height': '#(size)' },
		overrides: [ {
			element: 'line-height', attributes: { 'size': null }
		} ]
	};
};
