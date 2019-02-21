sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/m/MessageToast",
	"sap/m/MessageBox",
	"sap/ui/core/Fragment"
], function (Controller, MessageToast, MessageBox, Fragment) {
	"use strict";
	this.editOn = function (items, index) {
		items[index].getCells()[5].setEnabled(true);
		items[index].getCells()[6].setEnabled(false);
		items[index].getCells()[7].setEnabled(true);
	};
	this.editOff = function (items, index) {
		for (var j = 1; j < 3; j++) {
			items[index].getCells()[j].setEnabled(false);
		}
		items[index].getCells()[5].setEnabled(false);
		items[index].getCells()[7].setEnabled(false);
	};
	this.setEnabledOnSelectChange = function(aItems,index){
		if (!aItems[index].getCells()[6].getEnabled()) {
			for (var i = 0; i < aItems.length; i++) {
				aItems[i].getCells()[6].setEnabled(aItems[i].getSelected());
				editOff(aItems, i);
			}
		}
	}
	return Controller.extend("animal_display.controller.Display", {
		onInit: function () {},
		onSelect: function (oEvent) {
			var oTable = this.getView().byId("details");
			var aItems = oTable.getItems();
			var index = oTable.indexOfItem(oTable.getSelectedItem());
			setEnabledOnSelectChange(aItems,index);
		},
		animalsEdit: function () {
			var oTable = this.getView().byId("details");
			var aItems = oTable.getItems();
			var selItem = oTable.getSelectedItem();
			var index = oTable.indexOfItem(selItem);
			for (var j = 1; j < 3; j++) {
				aItems[index].getCells()[j].setEnabled(aItems[index].getSelected());
			}
			editOn(aItems, index);
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
			var oTable = this.getView().byId("details");
			var selItem = oTable.getSelectedItem();
			var aItems = oTable.getItems();
			var index = oTable.indexOfItem(selItem);
			var id = aItems[index].getCells()[0].getText();
			var name = aItems[index].getCells()[1].getValue();
			var kind = aItems[index].getCells()[2].getValue();
			var Animal = {
				aname: name,
				akind: kind,
				ts_update: null,
				ts_create: null
			};
			var oModel = this.getView().getModel("animals");
			oModel.update("/Animals('" + id + "')", Animal, {
				merge: false,
				success: function () {
					MessageToast.show("Animal was successfully updated.");
				},
				error: function () {
					MessageToast.show("Error.");
				}
			});
			editOff(aItems, index);
		},
		animalsDelete: function(){
			var oTable = this.getView().byId("details");
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
			var name = this.byId("newName").getValue();
			var kind = this.byId("newKind").getValue();
			if (!name || !kind) {
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
				var Animal = {
					aname: name,
					akind: kind
				};
				var oModel = this.getView().getModel("animals");
				oModel.create("/Animals", Animal, {
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