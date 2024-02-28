// See https://aka.ms/new-console-template for more information

using Lab14.Service;
using Lab14.UI;
using Lab14.Repository;

var documentFileRepository = new DocumentFileRepository();
var facturaFileRepository = new FacturaFileRepository();
var achizitieFileRepository = new AchizitieFileRepository();
facturaFileRepository.ReadSubEntities(achizitieFileRepository.FindAll());

var service = new Service(documentFileRepository, facturaFileRepository, achizitieFileRepository);

var ui = new Ui(service);
ui.Run();