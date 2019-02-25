sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/m/MessageToast",
	"sap/m/MessageBox",
	"sap/ui/core/Fragment"
], function (Controller, MessageToast, MessageBox, Fragment) {
	"use strict";
	this.editOn = function (items, index,that) {
		for (var j = 1; j < 3; j++) {
			items[index].getCells()[j].setEnabled(items[index].getSelected());
			}
			items[index].getCells()[that.config.saveButton].setEnabled(true);
			items[index].getCells()[that.config.editButton].setEnabled(false);
			items[index].getCells()[that.config.deleteButton].setEnabled(true);
		};
	this.editOff = function (items, index,that) {
		for (var j = 1; j < 3; j++) {
			items[index].getCells()[j].setEnabled(false);
		}
		items[index].getCells()[that.config.saveButton].setEnabled(false);
		items[index].getCells()[that.config.deleteButton].setEnabled(false);
	};
	this.setEnabledOnSelectChange = function(aItems,index,that){
		if (!aItems[index].getCells()[that.config.editButton].getEnabled()) {
			for (var i = 0; i < aItems.length; i++) {
				aItems[i].getCells()[that.config.editButton].setEnabled(aItems[i].getSelected());
				editOff(aItems, i, that);
			}
		}
	};
	this.getCurrTable =function(that){
		return that.getView().byId("details");
	};
	this.getAModel = function(that){
		return that.getView().getModel("animals");
	};
	return Controller.extend("animal_display.controller.Display", {
		onInit: function () {
			this.config = this.getView().getModel('config').getData();
			this.animal = this.getView().getModel('animal');
			this.animals = getAModel(this);

		},
		onSelect: function () {
			var oTable = getCurrTable(this);
			var aItems = oTable.getItems();
			var index = oTable.indexOfItem(oTable.getSelectedItem());
			setEnabledOnSelectChange(aItems,index,this);
		},
		animalsEdit: function () {
			var oTable = getCurrTable(this);
			var aItems = oTable.getItems();
			var selItem = oTable.getSelectedItem();
			var index = oTable.indexOfItem(selItem);
			editOn(aItems, index, this);
		},
		showCreateDialog: function () {
			var oView = this.getView();
			if (!this.byId("createDialog")) {
				Fragment.load({
					id: oView.getId(),
					type: "XML",
					name: "animal_display.view.Create",
					controller: this
				}).then(function (oDialog) {
					oView.addDependent(oDialog);
					oDialog.open();
				});
			} else {
				this.byId("createDialog").open();
			}
		},
		onCloseDialog: function () {
			this.byId("createDialog").close();
		},
		animalsUpdate: function () {
			var oTable = getCurrTable(this);
			var selItem = oTable.getSelectedItem();
			var aItems = oTable.getItems();
			var index = oTable.indexOfItem(selItem);
			var obj = this.animal.getData();
			obj.aid = aItems[index].getCells()[this.config.id].getText();
			obj.aname = aItems[index].getCells()[this.config.name].getValue();
			obj.akind = aItems[index].getCells()[this.config.kind].getValue();
			this.animals.update("/Animals('" + obj.aid + "')", obj, {
				merge: false,
				success: function () {
					MessageToast.show("Animal was successfully updated.");
				},
				error: function () {
					MessageToast.show("Error.");
				}
			});
			editOff(aItems, index, this);
		},
		animalsDelete: function(){
			var oTable = getCurrTable(this);
            var selItem = oTable.getSelectedItem();
            var id = selItem.getBindingContext("animals").getObject().aid;
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "https://p2001081098trial-p2001081098trial-dev-router.cfapps.eu10.hana.ondemand.com/api/xsjs/hw3.xsodata/Animals?aid=" + id,
                "method": "DELETE",
                "headers": {
                    "content-type": "application/json"
                },
                "processData": false
            };
            $.ajax(settings).done(function (response) {
                selItem.getBindingContext("animals").getModel().refresh(true);
            });
		},
		animalsCreate: function () {
			var obj = this.animal.getData();
			delete obj.ts_update;
			delete obj.ts_create;
			if (!obj.aname || !obj.akind) {
				var dialog = new sap.m.Dialog({
					title: 'Error',
					type: 'Message',
					state: 'Error',
					content: new sap.m.Text({
						text: 'Enter all necessarry field'
					}),
					beginButton: new sap.m.Button({
						text: 'OK',
						press: function () {
							dialog.close();
						}
					}),
					afterClose: function () {
						dialog.destroy();
					}
				});
				dialog.open();
			} else {
				this.animals.create("/Animals", obj, {
					success: function () {
						MessageToast.show("Animal was successfully added.");
					},
					error: function () {
						MessageToast.show("Error.");
					}
				});
				this.byId("createDialog").close();
			}
		}
	});
});