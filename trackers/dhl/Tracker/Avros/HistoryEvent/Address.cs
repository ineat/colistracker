// ------------------------------------------------------------------------------
// <auto-generated>
//    Generated by avrogen, version 1.7.7.5
//    Changes to this file may cause incorrect behavior and will be lost if code
//    is regenerated
// </auto-generated>
// ------------------------------------------------------------------------------
namespace Avros.HistoryEvent
{
	using System;
	using System.Collections.Generic;
	using System.Text;
	using global::Avro;
	using global::Avro.Specific;
	
	public partial class Address : ISpecificRecord
	{
		public static Schema _SCHEMA = Schema.Parse(@"{""type"":""record"",""name"":""Address"",""namespace"":""Avros.HistoryEvent"",""fields"":[{""name"":""CountryCode"",""type"":[""null"",""string""]},{""name"":""City"",""type"":[""null"",""string""]},{""name"":""ZipCode"",""type"":[""null"",""string""]},{""name"":""Localisation"",""type"":[""null"",""string""]}]}");
		private string _CountryCode;
		private string _City;
		private string _ZipCode;
		private string _Localisation;
		public virtual Schema Schema
		{
			get
			{
				return Address._SCHEMA;
			}
		}
		public string CountryCode
		{
			get
			{
				return this._CountryCode;
			}
			set
			{
				this._CountryCode = value;
			}
		}
		public string City
		{
			get
			{
				return this._City;
			}
			set
			{
				this._City = value;
			}
		}
		public string ZipCode
		{
			get
			{
				return this._ZipCode;
			}
			set
			{
				this._ZipCode = value;
			}
		}
		public string Localisation
		{
			get
			{
				return this._Localisation;
			}
			set
			{
				this._Localisation = value;
			}
		}
		public virtual object Get(int fieldPos)
		{
			switch (fieldPos)
			{
			case 0: return this.CountryCode;
			case 1: return this.City;
			case 2: return this.ZipCode;
			case 3: return this.Localisation;
			default: throw new AvroRuntimeException("Bad index " + fieldPos + " in Get()");
			};
		}
		public virtual void Put(int fieldPos, object fieldValue)
		{
			switch (fieldPos)
			{
			case 0: this.CountryCode = (System.String)fieldValue; break;
			case 1: this.City = (System.String)fieldValue; break;
			case 2: this.ZipCode = (System.String)fieldValue; break;
			case 3: this.Localisation = (System.String)fieldValue; break;
			default: throw new AvroRuntimeException("Bad index " + fieldPos + " in Put()");
			};
		}
	}
}