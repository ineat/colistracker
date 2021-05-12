using AutoFixture;
using AutoFixture.Kernel;
using System;
using System.Reflection;

namespace UnitTests.Configuration
{
    public class IgnoreVirtualMembers : ISpecimenBuilder
    {
        public object Create(object request, ISpecimenContext context)
        {
            if (context == null)
            {
                throw new ArgumentNullException(nameof(context));
            }

            var pi = request as PropertyInfo;
            if (pi == null)
            {
                return new NoSpecimen();
            }

#pragma warning disable CS8602 // Déréférencement d'une éventuelle référence null.
            if (pi.GetGetMethod().IsVirtual)
#pragma warning restore CS8602 // Déréférencement d'une éventuelle référence null.
            {
#pragma warning disable CS8603 // Existence possible d'un retour de référence null.
                return null;
#pragma warning restore CS8603 // Existence possible d'un retour de référence null.
            }

            return new NoSpecimen();
        }
    }

    public class IgnoreVirtualMembersCustomisation : ICustomization
    {
        public void Customize(IFixture fixture)
        {
            fixture.Customizations.Add(new IgnoreVirtualMembers());
        }
    }
}